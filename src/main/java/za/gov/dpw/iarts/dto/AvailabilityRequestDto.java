package za.gov.dpw.iarts.dto;

import jakarta.validation.constraints.NotBlank;

public record AvailabilityRequestDto(String referenceNumber, @NotBlank String equipment, String rank) {}
