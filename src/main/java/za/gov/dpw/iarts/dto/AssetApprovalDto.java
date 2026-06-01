package za.gov.dpw.iarts.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AssetApprovalDto(
        @NotNull Long requestId,
        @NotBlank String movableAssetName,
        @NotBlank String approvalDate,
        @NotBlank String signatureFileName,
        @NotBlank String signatureContentType,
        @NotBlank String signatureBase64
) {
}
