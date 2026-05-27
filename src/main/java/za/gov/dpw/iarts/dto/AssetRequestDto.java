package za.gov.dpw.iarts.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
public record AssetRequestDto(Long id, @NotNull Long requesterId, Long departmentId, @NotNull String assetType, @NotBlank String justification, String status, boolean printerRoutedToQts, String qtsReference, String stockVerificationRemarks) {}
