package za.gov.dpw.iarts.dto;

import jakarta.validation.constraints.NotBlank;

public record IntraRequestDto(
        @NotBlank String referenceNumber,
        String itpNumber,
        String orderNumber,
        @NotBlank String chiefDirectorate,
        @NotBlank String subDirectorate,
        String objective,
        String responsibility,
        @NotBlank String chiefUser,
        String callReference,
        String currentOwner,
        String currentBuilding,
        String currentFloor,
        String currentOffice,
        String currentRegion,
        String currentContact,
        String destinationOwner,
        String destinationBuilding,
        String destinationFloor,
        String destinationOffice,
        String destinationRegion,
        String destinationContact,
        String movementReason,
        String destinationSignatureDate,
        String destinationSignatureFileName,
        String destinationSignatureContentType,
        String destinationSignatureBase64
) {}
