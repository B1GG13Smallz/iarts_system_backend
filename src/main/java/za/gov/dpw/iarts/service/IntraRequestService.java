package za.gov.dpw.iarts.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import za.gov.dpw.iarts.dto.IntraRequestDto;
import za.gov.dpw.iarts.dto.IntraRequestResponseDto;
import za.gov.dpw.iarts.dto.IntraRequestStatusDto;
import za.gov.dpw.iarts.dto.TechnicianRequestDetailsDto;
import za.gov.dpw.iarts.entity.AvailabilityRequest;
import za.gov.dpw.iarts.entity.IntraRequestSignature;
import za.gov.dpw.iarts.entity.IntraRequest;
import za.gov.dpw.iarts.entity.User;
import za.gov.dpw.iarts.exception.ResourceNotFoundException;
import za.gov.dpw.iarts.repository.AssetApprovalRepository;
import za.gov.dpw.iarts.repository.AvailabilityRequestRepository;
import za.gov.dpw.iarts.repository.IntraRequestRepository;
import za.gov.dpw.iarts.repository.IntraRequestSignatureRepository;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IntraRequestService {
    private static final List<String> ALLOWED_STATUSES = List.of("SUBMITTED", "ASSIGNED", "IN_PROGRESS", "READY_FOR_DELIVERY", "COMPLETED");

    private final IntraRequestRepository intraRequestRepository;
    private final IntraRequestSignatureRepository intraRequestSignatureRepository;
    private final AssetApprovalRepository assetApprovalRepository;
    private final AvailabilityRequestRepository availabilityRequestRepository;

    public IntraRequestResponseDto create(User requester, IntraRequestDto dto) {
        IntraRequest request = new IntraRequest();
        request.setRequester(requester);
        request.setReferenceNumber(dto.referenceNumber());
        request.setItpNumber(dto.itpNumber());
        request.setOrderNumber(dto.orderNumber());
        request.setChiefDirectorate(dto.chiefDirectorate());
        request.setSubDirectorate(dto.subDirectorate());
        request.setObjective(dto.objective());
        request.setResponsibility(dto.responsibility());
        request.setChiefUser(dto.chiefUser());
        request.setCallReference(dto.callReference());
        request.setCurrentOwner(dto.currentOwner());
        request.setCurrentBuilding(dto.currentBuilding());
        request.setCurrentFloor(dto.currentFloor());
        request.setCurrentOffice(dto.currentOffice());
        request.setCurrentRegion(dto.currentRegion());
        request.setCurrentContact(dto.currentContact());
        request.setDestinationOwner(dto.destinationOwner());
        request.setDestinationBuilding(dto.destinationBuilding());
        request.setDestinationFloor(dto.destinationFloor());
        request.setDestinationOffice(dto.destinationOffice());
        request.setDestinationRegion(dto.destinationRegion());
        request.setDestinationContact(dto.destinationContact());
        request.setMovementReason(dto.movementReason());
        IntraRequest savedRequest = intraRequestRepository.save(request);
        saveDestinationSignature(savedRequest, dto);
        return toResponse(savedRequest);
    }

    public List<IntraRequestResponseDto> findAll() {
        return intraRequestRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<IntraRequestResponseDto> findMine(User requester) {
        return intraRequestRepository.findByRequesterOrderByCreatedAtDesc(requester)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public TechnicianRequestDetailsDto findTechnicianDetailsByReference(String referenceNumber) {
        IntraRequest request = intraRequestRepository.findFirstByReferenceNumberOrderByCreatedAtDesc(referenceNumber)
                .orElseThrow(() -> new ResourceNotFoundException("INTRA request not found"));
        Optional<AvailabilityRequest> availabilityRequest = availabilityRequestRepository.findFirstByReferenceNumberOrderByCreatedAtDesc(referenceNumber);
        return new TechnicianRequestDetailsDto(
                request,
                availabilityRequest.map(AvailabilityRequest::getStatus).orElse("UNKNOWN"),
                availabilityRequest.map(AvailabilityRequest::getEquipment).orElse(""),
                availabilityRequest.map(AvailabilityRequest::getDescription).orElse(""),
                availabilityRequest.map(AvailabilityRequest::getSerialNumber).orElse(""),
                availabilityRequest.map(AvailabilityRequest::getBarCodeNumber).orElse("")
        );
    }

    public IntraRequestResponseDto updateStatus(Long id, IntraRequestStatusDto dto) {
        String status = dto.status().toUpperCase();
        if (!ALLOWED_STATUSES.contains(status)) {
            throw new IllegalArgumentException("Unsupported INTRA request status");
        }
        IntraRequest request = intraRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("INTRA request not found"));
        request.setStatus(status);
        return toResponse(intraRequestRepository.save(request));
    }

    private void saveDestinationSignature(IntraRequest request, IntraRequestDto dto) {
        if (isBlank(dto.destinationSignatureDate()) && isBlank(dto.destinationSignatureBase64())) {
            return;
        }

        IntraRequestSignature signature = new IntraRequestSignature();
        signature.setRequest(request);
        signature.setSignatureDate(parseDate(dto.destinationSignatureDate(), "Destination signature date is invalid"));
        signature.setSignatureFileName(dto.destinationSignatureFileName());
        signature.setSignatureContentType(dto.destinationSignatureContentType());
        signature.setSignatureData(decodeSignature(dto.destinationSignatureBase64()));
        intraRequestSignatureRepository.save(signature);
    }

    private IntraRequestResponseDto toResponse(IntraRequest request) {
        return IntraRequestResponseDto.from(
                request,
                intraRequestSignatureRepository.findByRequestId(request.getId()),
                assetApprovalRepository.findFirstByRequestIdOrderByCreatedAtDesc(request.getId())
        );
    }

    private LocalDate parseDate(String value, String errorMessage) {
        if (isBlank(value)) {
            return null;
        }

        try {
            return LocalDate.parse(value);
        } catch (DateTimeParseException ex) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    private byte[] decodeSignature(String signatureBase64) {
        if (isBlank(signatureBase64)) {
            return null;
        }

        try {
            return Base64.getDecoder().decode(signatureBase64);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Destination signature file is not valid base64");
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
