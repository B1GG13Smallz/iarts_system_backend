package za.gov.dpw.iarts.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DamageReportDto(Long id, @NotNull Long equipmentId, @NotNull Long reportedById, @NotBlank String incidentDescription, String warrantyReference, String followUpNotes) {}
