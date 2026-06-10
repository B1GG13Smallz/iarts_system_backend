package za.gov.dpw.iarts.dto;

import jakarta.validation.constraints.NotBlank;

public record AssetApprovalDto(
        Long requestId,
        Long permissionRemovalId,
        @NotBlank String movableAssetName,
        @NotBlank String approvalDate,
        @NotBlank String signatureFileName,
        @NotBlank String signatureContentType,
        @NotBlank String signatureBase64
) {
}
