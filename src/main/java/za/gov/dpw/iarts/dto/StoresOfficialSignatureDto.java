package za.gov.dpw.iarts.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record StoresOfficialSignatureDto(
        @NotBlank String storesOfficialName,
        @Valid @NotNull RegisterSignatureDto storesOfficialSignOut
) {
}
