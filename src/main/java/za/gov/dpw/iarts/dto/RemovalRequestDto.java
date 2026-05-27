package za.gov.dpw.iarts.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record RemovalRequestDto(Long id, @NotNull Long requesterId, @NotNull Long equipmentId, String reason, LocalDate removalDate, LocalDate expectedReturnDate, Long ictApproverId, Long mamApproverId, Long securityValidatorId, String comments) {}
