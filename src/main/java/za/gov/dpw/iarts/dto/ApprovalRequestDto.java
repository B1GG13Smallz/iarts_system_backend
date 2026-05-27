package za.gov.dpw.iarts.dto;

import jakarta.validation.constraints.NotNull;
public record ApprovalRequestDto(@NotNull Long approverId, @NotNull String decision, String comments) {}
