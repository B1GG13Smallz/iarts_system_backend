package za.gov.dpw.iarts.dto;

public record PermissionRemovalRecordDto(
        Long id,
        String workflowStatus,
        String officialName,
        String unitDirectorateBranch,
        String telephoneNumber,
        String identityOrPersalNumber,
        String removalReason,
        PermissionRemovalSignatureDto officialSignature,
        String equipmentDescription,
        String barCode,
        String serialNumber,
        String currentLocation,
        String period,
        String newLocation,
        PermissionRemovalSignatureDto ictSignature,
        String ictDate,
        PermissionRemovalSignatureDto mamSignature,
        String mamDate,
        PermissionRemovalSignatureDto securitySignature,
        String securityDate,
        String createdByUsername
) {
}
