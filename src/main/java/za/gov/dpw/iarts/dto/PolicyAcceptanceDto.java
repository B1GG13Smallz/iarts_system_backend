package za.gov.dpw.iarts.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PolicyAcceptanceDto(Long id, @NotNull Long userId, @NotNull Long equipmentId, @NotBlank String policyName, String acceptanceText) {}
