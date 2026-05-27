package za.gov.dpw.iarts.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record TakeHomeRequestDto(Long id, @NotNull Long requesterId, @NotNull Long equipmentId, String requesterCategory, LocalDate startDate, LocalDate endDate, String reason, Long approverId) {}
