package za.gov.dpw.iarts.dto;

import jakarta.validation.Valid;

public record PermissionRemovalDto(
        String officialName,
        String unitDirectorateBranch,
        String telephoneNumber,
        String identityOrPersalNumber,
        String removalReason,
        @Valid PermissionRemovalSignatureDto officialSignature,
        String equipmentDescription,
        String barCode,
        String serialNumber,
        String currentLocation,
        String period,
        String newLocation,
        @Valid PermissionRemovalSignatureDto ictSignature,
        String ictDate,
        @Valid PermissionRemovalSignatureDto mamSignature,
        String mamDate,
        @Valid PermissionRemovalSignatureDto securitySignature,
        String securityDate
) {
}
