package za.gov.dpw.iarts.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterEntryDto(
        @NotBlank String registerType,
        @NotBlank String dateOut,
        @NotBlank String itemDescription,
        @NotBlank String serialNumber,
        @NotBlank String barCode,
        String orderNumber,
        @NotBlank String userFullName,
        String extension,
        String roomNumber,
        @Valid @NotNull RegisterSignatureDto userSignOut,
        String storesOfficialName,
        @Valid @NotNull RegisterSignatureDto storesOfficialSignOut,
        String comment
) {
}
