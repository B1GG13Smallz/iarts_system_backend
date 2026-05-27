package za.gov.dpw.iarts.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import za.gov.dpw.iarts.dto.AssignmentDto;
import za.gov.dpw.iarts.dto.EquipmentDto;
import za.gov.dpw.iarts.dto.PolicyAcceptanceDto;
import za.gov.dpw.iarts.entity.Assignment;
import za.gov.dpw.iarts.entity.Equipment;
import za.gov.dpw.iarts.entity.PolicyAcceptance;
import za.gov.dpw.iarts.service.AssetService;

@RestController
@RequiredArgsConstructor
public class AssetController {
    private final AssetService assetService;

    @PostMapping("/api/assets")
    public Equipment createEquipment(@Valid @RequestBody EquipmentDto dto) {
        return assetService.createEquipment(dto);
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
