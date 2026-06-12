package za.gov.dpw.iarts.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import za.gov.dpw.iarts.dto.AssignmentDto;
import za.gov.dpw.iarts.dto.EquipmentDto;
import za.gov.dpw.iarts.dto.EquipmentStockDto;
import za.gov.dpw.iarts.dto.PolicyAcceptanceDto;
import za.gov.dpw.iarts.entity.Assignment;
import za.gov.dpw.iarts.entity.Equipment;
import za.gov.dpw.iarts.entity.PolicyAcceptance;
import za.gov.dpw.iarts.service.AssetCaptureService;
import za.gov.dpw.iarts.service.AssetService;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class AssetController {
    private final AssetService assetService;
    private final AssetCaptureService assetCaptureService;

    @GetMapping("/api/assets")
    @PreAuthorize("hasAnyRole('ADMIN', 'ICT_STOREROOM', 'ASSET_MANAGEMENT', 'END_USER')")
    public List<EquipmentStockDto> equipmentStock() {
        return assetCaptureService.findAll();
    }

    @GetMapping("/api/assets/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ICT_STOREROOM', 'ASSET_MANAGEMENT', 'END_USER')")
    public EquipmentStockDto equipmentStockById(@PathVariable Long id) {
        return assetCaptureService.findById(id);
    }

    @PostMapping("/api/assets")
    @PreAuthorize("hasAnyRole('ADMIN', 'ICT_STOREROOM')")
    public EquipmentStockDto createEquipment(@Valid @RequestBody EquipmentStockDto dto) {
        return assetCaptureService.create(dto);
    }

    @PutMapping("/api/assets/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ICT_STOREROOM')")
    public EquipmentStockDto updateEquipment(@PathVariable Long id, @Valid @RequestBody EquipmentStockDto dto) {
        return assetCaptureService.update(id, dto);
    }

    @DeleteMapping("/api/assets/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ICT_STOREROOM')")
    public ResponseEntity<Void> deleteEquipment(@PathVariable Long id) {
        assetCaptureService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/api/assets/assign")
    public Assignment assign(@Valid @RequestBody AssignmentDto dto) {
        return assetService.assign(dto);
    }

    @PostMapping("/api/policies/accept")
    public PolicyAcceptance acceptPolicy(@Valid @RequestBody PolicyAcceptanceDto dto) {
        return assetService.acceptPolicy(dto);
    }
}
