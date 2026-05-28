package za.gov.dpw.iarts.dto;

import jakarta.validation.constraints.NotBlank;

public record AvailabilityRequestDto(@NotBlank String referenceNumber, @NotBlank String equipment) {}
