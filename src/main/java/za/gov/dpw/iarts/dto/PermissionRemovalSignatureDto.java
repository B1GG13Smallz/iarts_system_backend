package za.gov.dpw.iarts.dto;

import jakarta.validation.constraints.NotBlank;

public record PermissionRemovalSignatureDto(
        @NotBlank String fileName,
        @NotBlank String contentType,
        @NotBlank String base64
) {
}
