package za.gov.dpw.iarts.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EquipmentStockDto(
        Long id,
        @NotBlank String assetTag,
        String serialNumber,
        @NotNull String assetType,
        String make,
        String model,
        String location,
        String netTrackReference,
        boolean laptopPolicyRequired,
        Long stockRecordId,
        String stockStatus,
        String storeroomLocation,
        String remarks) {
}
