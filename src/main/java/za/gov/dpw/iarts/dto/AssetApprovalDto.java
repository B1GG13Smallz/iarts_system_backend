package za.gov.dpw.iarts.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record AssetApprovalDto(
        @NotNull Long requestId,
        @NotBlank String movableAssetName,
        @NotNull LocalDateTime approvalDate,
        @NotBlank String signatureFileName,
        @NotBlank String signatureContentType,
        @NotBlank String signatureBase64
) {
}
