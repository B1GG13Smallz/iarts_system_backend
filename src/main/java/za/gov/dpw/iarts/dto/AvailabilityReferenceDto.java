package za.gov.dpw.iarts.dto;

import jakarta.validation.constraints.NotBlank;

public record AvailabilityReferenceDto(@NotBlank String referenceNumber) {}
