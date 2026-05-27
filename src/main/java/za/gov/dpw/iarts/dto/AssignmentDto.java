package za.gov.dpw.iarts.dto;

import jakarta.validation.constraints.NotNull;

public record AssignmentDto(Long id, @NotNull Long equipmentId, @NotNull Long assignedToId, @NotNull Long issuedById, Long requestId) {}
