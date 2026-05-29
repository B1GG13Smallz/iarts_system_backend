package za.gov.dpw.iarts.dto;

import jakarta.validation.constraints.NotBlank;

public record AvailabilityStatusDto(
        @NotBlank String status,
        String description,
        String serialNumber,
        String barCodeNumber
) {}
