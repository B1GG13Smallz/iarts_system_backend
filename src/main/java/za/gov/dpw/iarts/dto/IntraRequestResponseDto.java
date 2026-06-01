package za.gov.dpw.iarts.dto;

import za.gov.dpw.iarts.entity.AssetApproval;
import za.gov.dpw.iarts.entity.IntraRequest;
import za.gov.dpw.iarts.entity.IntraRequestSignature;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

public record IntraRequestResponseDto(
        Long id,
        String referenceNumber,
        String itpNumber,
        String orderNumber,
        String chiefDirectorate,
        String subDirectorate,
        String objective,
        String responsibility,
        String chiefUser,
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
        String status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDate destinationSignatureDate,
        String destinationSignatureFileName,
        String destinationSignatureContentType,
        boolean destinationSignatureCaptured,
        LocalDateTime assetApprovalDate,
        String assetApprovalSignatureFileName,
        String assetApprovalSignatureContentType,
        boolean assetApprovalSignatureCaptured
) {
    public static IntraRequestResponseDto from(
            IntraRequest request,
            Optional<IntraRequestSignature> signature,
            Optional<AssetApproval> assetApproval
    ) {
        return new IntraRequestResponseDto(
                request.getId(),
                request.getReferenceNumber(),
                request.getItpNumber(),
                request.getOrderNumber(),
                request.getChiefDirectorate(),
                request.getSubDirectorate(),
                request.getObjective(),
                request.getResponsibility(),
                request.getChiefUser(),
                request.getCallReference(),
                request.getCurrentOwner(),
                request.getCurrentBuilding(),
                request.getCurrentFloor(),
                request.getCurrentOffice(),
                request.getCurrentRegion(),
                request.getCurrentContact(),
                request.getDestinationOwner(),
                request.getDestinationBuilding(),
                request.getDestinationFloor(),
                request.getDestinationOffice(),
                request.getDestinationRegion(),
                request.getDestinationContact(),
                request.getMovementReason(),
                request.getStatus(),
                request.getCreatedAt(),
                request.getUpdatedAt(),
                signature.map(IntraRequestSignature::getSignatureDate).orElse(null),
                signature.map(IntraRequestSignature::getSignatureFileName).orElse(null),
                signature.map(IntraRequestSignature::getSignatureContentType).orElse(null),
                signature.isPresent(),
                assetApproval.map(AssetApproval::getApprovalDate).orElse(null),
                assetApproval.map(AssetApproval::getSignatureFileName).orElse(null),
                assetApproval.map(AssetApproval::getSignatureContentType).orElse(null),
                assetApproval.isPresent()
        );
    }
}
