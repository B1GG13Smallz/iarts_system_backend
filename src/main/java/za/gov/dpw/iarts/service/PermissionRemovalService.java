package za.gov.dpw.iarts.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import za.gov.dpw.iarts.constants.PermissionRemovalStatuses;
import za.gov.dpw.iarts.dto.PermissionRemovalDto;
import za.gov.dpw.iarts.dto.PermissionRemovalRecordDto;
import za.gov.dpw.iarts.dto.PermissionRemovalSignatureDto;
import za.gov.dpw.iarts.entity.PermissionRemoval;
import za.gov.dpw.iarts.entity.PermissionRemovalPdf;
import za.gov.dpw.iarts.entity.User;
import za.gov.dpw.iarts.exception.ResourceNotFoundException;
import za.gov.dpw.iarts.repository.PermissionRemovalPdfRepository;
import za.gov.dpw.iarts.repository.PermissionRemovalRepository;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Base64;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PermissionRemovalService {
    private final PermissionRemovalRepository permissionRemovalRepository;
    private final PermissionRemovalPdfRepository permissionRemovalPdfRepository;
    private final PermissionRemovalPdfService permissionRemovalPdfService;

    @Transactional
    public PermissionRemoval create(User createdBy, PermissionRemovalDto dto) {
        return createWithStatus(createdBy, dto, PermissionRemovalStatuses.SAVED);
    }

    @Transactional
    public PermissionRemoval sendToStoreroom(User createdBy, PermissionRemovalDto dto) {
        return createWithStatus(createdBy, dto, PermissionRemovalStatuses.SENT_TO_STOREROOM);
    }

    @Transactional
    public PermissionRemoval signIctAndSendToAssets(Long id, PermissionRemovalDto dto) {
        PermissionRemoval removal = permissionRemovalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Permission removal form not found"));

        applySignature(dto.ictSignature(), removal::setIctSignatureFileName, removal::setIctSignatureContentType, removal::setIctSignatureData);
        removal.setIctDate(parseOptionalDate(dto.ictDate(), "ICT date"));
        removal.setWorkflowStatus(PermissionRemovalStatuses.SENT_TO_ASSETS);
        PermissionRemoval savedRemoval = permissionRemovalRepository.save(removal);
        savePdf(savedRemoval, toDto(savedRemoval));
        return savedRemoval;
    }

    @Transactional
    public PermissionRemoval markAssetsApproved(Long id) {
        PermissionRemoval removal = permissionRemovalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Permission removal form not found"));
        removal.setWorkflowStatus(PermissionRemovalStatuses.ASSETS_APPROVED);
        return permissionRemovalRepository.save(removal);
    }

    @Transactional
    public PermissionRemoval saveAssetsApproval(Long id, PermissionRemovalDto dto) {
        PermissionRemoval removal = permissionRemovalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Permission removal form not found"));

        applySignature(dto.mamSignature(), removal::setMamSignatureFileName, removal::setMamSignatureContentType, removal::setMamSignatureData);
        removal.setMamDate(parseOptionalDate(dto.mamDate(), "MAM date"));
        removal.setWorkflowStatus(PermissionRemovalStatuses.ASSETS_APPROVED);
        PermissionRemoval savedRemoval = permissionRemovalRepository.save(removal);
        savePdf(savedRemoval, toDto(savedRemoval));
        return savedRemoval;
    }

    @Transactional(readOnly = true)
    public List<PermissionRemovalRecordDto> search(String identityOrPersalNumber, String workflowStatus) {
        List<PermissionRemoval> removals;
        if (!isBlank(identityOrPersalNumber) && !isBlank(workflowStatus)) {
            removals = permissionRemovalRepository.searchByIdentityOrPersalNumberAndWorkflowStatus(identityOrPersalNumber, workflowStatus);
        } else if (!isBlank(identityOrPersalNumber)) {
            removals = permissionRemovalRepository.searchByIdentityOrPersalNumber(identityOrPersalNumber);
        } else if (!isBlank(workflowStatus)) {
            removals = permissionRemovalRepository.findByWorkflowStatusOrderByCreatedAtDesc(workflowStatus);
        } else {
            removals = permissionRemovalRepository.findAll();
        }

        return removals.stream().map(this::toRecordDto).toList();
    }

    @Transactional(readOnly = true)
    public PermissionRemovalRecordDto getRecord(Long id) {
        return permissionRemovalRepository.findById(id)
                .map(this::toRecordDto)
                .orElseThrow(() -> new ResourceNotFoundException("Permission removal form not found"));
    }

    private PermissionRemoval createWithStatus(User createdBy, PermissionRemovalDto dto, String workflowStatus) {
        if (isBlank(dto.officialName()) || isBlank(dto.equipmentDescription()) || isBlank(dto.barCode()) || isBlank(dto.serialNumber())) {
            throw new IllegalArgumentException("Official name, equipment description, bar code and serial number are required");
        }

        PermissionRemoval removal = new PermissionRemoval();
        removal.setCreatedBy(createdBy);
        removal.setOfficialName(dto.officialName());
        removal.setUnitDirectorateBranch(dto.unitDirectorateBranch());
        removal.setTelephoneNumber(dto.telephoneNumber());
        removal.setIdentityOrPersalNumber(dto.identityOrPersalNumber());
        removal.setRemovalReason(dto.removalReason());
        applySignature(dto.officialSignature(), removal::setOfficialSignatureFileName, removal::setOfficialSignatureContentType, removal::setOfficialSignatureData);
        removal.setEquipmentDescription(dto.equipmentDescription());
        removal.setBarCode(dto.barCode());
        removal.setSerialNumber(dto.serialNumber());
        removal.setCurrentLocation(dto.currentLocation());
        removal.setPeriod(dto.period());
        removal.setNewLocation(dto.newLocation());
        applySignature(dto.ictSignature(), removal::setIctSignatureFileName, removal::setIctSignatureContentType, removal::setIctSignatureData);
        removal.setIctDate(parseOptionalDate(dto.ictDate(), "ICT date"));
        applySignature(dto.mamSignature(), removal::setMamSignatureFileName, removal::setMamSignatureContentType, removal::setMamSignatureData);
        removal.setMamDate(parseOptionalDate(dto.mamDate(), "MAM date"));
        applySignature(dto.securitySignature(), removal::setSecuritySignatureFileName, removal::setSecuritySignatureContentType, removal::setSecuritySignatureData);
        removal.setSecurityDate(parseOptionalDate(dto.securityDate(), "Security date"));
        removal.setWorkflowStatus(workflowStatus);
        PermissionRemoval savedRemoval = permissionRemovalRepository.save(removal);
        savePdf(savedRemoval, dto);
        return savedRemoval;
    }

    private void savePdf(PermissionRemoval removal, PermissionRemovalDto dto) {
        PermissionRemovalPdf pdf = permissionRemovalPdfRepository.findByPermissionRemovalId(removal.getId())
                .orElseGet(PermissionRemovalPdf::new);
        if (pdf.getPermissionRemoval() == null) {
            pdf.setPermissionRemoval(removal);
        }
        pdf.setFileName("permission-to-remove-equipment-" + removal.getId() + ".pdf");
        pdf.setContentType("application/pdf");
        pdf.setData(permissionRemovalPdfService.generate(dto));
        permissionRemovalPdfRepository.save(pdf);
    }

    private PermissionRemovalRecordDto toRecordDto(PermissionRemoval removal) {
        return new PermissionRemovalRecordDto(
                removal.getId(),
                removal.getWorkflowStatus(),
                removal.getOfficialName(),
                removal.getUnitDirectorateBranch(),
                removal.getTelephoneNumber(),
                removal.getIdentityOrPersalNumber(),
                removal.getRemovalReason(),
                signature(removal.getOfficialSignatureFileName(), removal.getOfficialSignatureContentType(), removal.getOfficialSignatureData()),
                removal.getEquipmentDescription(),
                removal.getBarCode(),
                removal.getSerialNumber(),
                removal.getCurrentLocation(),
                removal.getPeriod(),
                removal.getNewLocation(),
                signature(removal.getIctSignatureFileName(), removal.getIctSignatureContentType(), removal.getIctSignatureData()),
                formatDate(removal.getIctDate()),
                signature(removal.getMamSignatureFileName(), removal.getMamSignatureContentType(), removal.getMamSignatureData()),
                formatDate(removal.getMamDate()),
                signature(removal.getSecuritySignatureFileName(), removal.getSecuritySignatureContentType(), removal.getSecuritySignatureData()),
                formatDate(removal.getSecurityDate()),
                removal.getCreatedBy().getUsername()
        );
    }

    private PermissionRemovalDto toDto(PermissionRemoval removal) {
        PermissionRemovalRecordDto record = toRecordDto(removal);
        return new PermissionRemovalDto(
                record.officialName(),
                record.unitDirectorateBranch(),
                record.telephoneNumber(),
                record.identityOrPersalNumber(),
                record.removalReason(),
                record.officialSignature(),
                record.equipmentDescription(),
                record.barCode(),
                record.serialNumber(),
                record.currentLocation(),
                record.period(),
                record.newLocation(),
                record.ictSignature(),
                record.ictDate(),
                record.mamSignature(),
                record.mamDate(),
                record.securitySignature(),
                record.securityDate()
        );
    }

    private PermissionRemovalSignatureDto signature(String fileName, String contentType, byte[] data) {
        if (fileName == null || contentType == null || data == null) {
            return null;
        }

        return new PermissionRemovalSignatureDto(fileName, contentType, Base64.getEncoder().encodeToString(data));
    }

    private String formatDate(LocalDate date) {
        return date == null ? "" : date.toString();
    }

    private void applySignature(
            PermissionRemovalSignatureDto signature,
            StringSetter fileNameSetter,
            StringSetter contentTypeSetter,
            BytesSetter dataSetter
    ) {
        if (signature == null) {
            return;
        }

        fileNameSetter.set(signature.fileName());
        contentTypeSetter.set(signature.contentType());
        dataSetter.set(decodeSignature(signature.base64()));
    }

    private byte[] decodeSignature(String signatureBase64) {
        try {
            return Base64.getDecoder().decode(signatureBase64);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Signature file is not valid base64");
        }
    }

    private LocalDate parseOptionalDate(String date, String label) {
        if (date == null || date.isBlank()) {
            return null;
        }

        try {
            return LocalDate.parse(date);
        } catch (DateTimeParseException ex) {
            throw new IllegalArgumentException(label + " is invalid");
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }

    private interface StringSetter {
        void set(String value);
    }

    private interface BytesSetter {
        void set(byte[] value);
    }
}
