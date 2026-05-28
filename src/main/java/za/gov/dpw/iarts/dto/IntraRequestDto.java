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
        String destinationOwner,
        String destinationBuilding,
        String destinationFloor,
        String destinationOffice,
        String destinationRegion,
        String destinationContact,
        String movementReason
) {}
