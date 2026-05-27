package za.gov.dpw.iarts.dto;

import jakarta.validation.constraints.NotNull;

public record MovementRequestDto(Long id, @NotNull Long requesterId, @NotNull Long equipmentId, String fromLocation, String toLocation, Long technicianId, Long assetManagementVerifierId, boolean clientConfirmed, String remarks) {}
