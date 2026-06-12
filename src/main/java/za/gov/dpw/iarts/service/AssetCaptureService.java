package za.gov.dpw.iarts.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import za.gov.dpw.iarts.constants.StockStatuses;
import za.gov.dpw.iarts.dto.EquipmentStockDto;
import za.gov.dpw.iarts.dto.StockSummaryDto;
import za.gov.dpw.iarts.entity.AssetCapture;
import za.gov.dpw.iarts.exception.ResourceNotFoundException;
import za.gov.dpw.iarts.repository.AssetCaptureRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssetCaptureService {
    private final AssetCaptureRepository assetCaptureRepository;

    @Transactional(readOnly = true)
    public List<EquipmentStockDto> findAll() {
        return assetCaptureRepository.findAll().stream().map(this::toDto).toList();
    }

    @Transactional(readOnly = true)
    public EquipmentStockDto findById(Long id) {
        return toDto(findAssetCapture(id));
    }

    @Transactional
    public EquipmentStockDto create(EquipmentStockDto dto) {
        AssetCapture assetCapture = new AssetCapture();
        applyFields(assetCapture, dto);

        return toDto(assetCaptureRepository.save(assetCapture));
    }

    @Transactional
    public EquipmentStockDto update(Long id, EquipmentStockDto dto) {
        AssetCapture assetCapture = findAssetCapture(id);
        applyFields(assetCapture, dto);

        return toDto(assetCaptureRepository.save(assetCapture));
    }

    @Transactional
    public void delete(Long id) {
        AssetCapture assetCapture = findAssetCapture(id);
        assetCaptureRepository.delete(assetCapture);
    }

    @Transactional(readOnly = true)
    public List<StockSummaryDto> stockSummary() {
        return StockStatuses.ALL.stream()
                .map(status -> new StockSummaryDto(status, assetCaptureRepository.countByStockStatus(status)))
                .toList();
    }

    @Transactional
    public void markAvailableAssetUnavailable(String assetType, String storageRank) {
        String normalizedAssetType = optional(assetType);
        String normalizedStorageRank = optional(storageRank);

        if (normalizedAssetType == null || normalizedStorageRank == null) {
            return;
        }

        assetCaptureRepository
                .findFirstByAssetTypeIgnoreCaseAndStorageRankIgnoreCaseAndStockStatusOrderByCreatedAtDesc(
                        normalizedAssetType,
                        normalizedStorageRank,
                        StockStatuses.AVAILABLE
                )
                .ifPresent(assetCapture -> {
                    assetCapture.setStockStatus(StockStatuses.UNAVAILABLE);
                    assetCaptureRepository.save(assetCapture);
                });
    }

    @Transactional
    public void markMatchingAssetAvailable(String assetType, String storageRank) {
        String normalizedAssetType = optional(assetType);
        String normalizedStorageRank = optional(storageRank);

        if (normalizedAssetType == null || normalizedStorageRank == null) {
            return;
        }

        assetCaptureRepository
                .findFirstByAssetTypeIgnoreCaseAndStorageRankIgnoreCaseAndStockStatusOrderByCreatedAtDesc(
                        normalizedAssetType,
                        normalizedStorageRank,
                        StockStatuses.UNAVAILABLE
                )
                .ifPresent(assetCapture -> {
                    assetCapture.setStockStatus(StockStatuses.AVAILABLE);
                    assetCaptureRepository.save(assetCapture);
                });
    }

    private AssetCapture findAssetCapture(Long id) {
        return assetCaptureRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Asset capture not found"));
    }

    private void applyFields(AssetCapture assetCapture, EquipmentStockDto dto) {
        assetCapture.setAssetTag(requiredUpper(dto.assetTag(), "Asset tag is required"));
        assetCapture.setSerialNumber(optional(dto.serialNumber()));
        assetCapture.setAssetType(required(dto.assetType(), "Asset type is required"));
        assetCapture.setMake(optional(dto.make()));
        assetCapture.setModel(optional(dto.model()));
        assetCapture.setLocation(optional(dto.location()));
        assetCapture.setNetTrackReference(optional(dto.netTrackReference()));
        assetCapture.setLaptopPolicyRequired(dto.laptopPolicyRequired());
        assetCapture.setStockStatus(defaultStatus(dto.stockStatus()));
        assetCapture.setStorageRank(optional(dto.storeroomLocation()));
        assetCapture.setRemarks(optional(dto.remarks()));
    }

    private EquipmentStockDto toDto(AssetCapture assetCapture) {
        return new EquipmentStockDto(
                assetCapture.getId(),
                assetCapture.getAssetTag(),
                assetCapture.getSerialNumber(),
                assetCapture.getAssetType(),
                assetCapture.getMake(),
                assetCapture.getModel(),
                assetCapture.getLocation(),
                assetCapture.getNetTrackReference(),
                assetCapture.isLaptopPolicyRequired(),
                null,
                assetCapture.getStockStatus(),
                assetCapture.getStorageRank(),
                assetCapture.getRemarks());
    }

    private String defaultStatus(String value) {
        String status = optional(value);

        return status == null ? StockStatuses.AVAILABLE : status;
    }

    private String optional(String value) {
        if (value == null) {
            return null;
        }

        String trimmed = value.trim();

        return trimmed.isEmpty() ? null : trimmed;
    }

    private String required(String value, String message) {
        String trimmed = optional(value);

        if (trimmed == null) {
            throw new IllegalArgumentException(message);
        }

        return trimmed;
    }

    private String requiredUpper(String value, String message) {
        return required(value, message).toUpperCase();
    }
}
