package za.gov.dpw.iarts.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import za.gov.dpw.iarts.dto.IntraRequestDto;
import za.gov.dpw.iarts.dto.IntraRequestStatusDto;
import za.gov.dpw.iarts.dto.TechnicianRequestDetailsDto;
import za.gov.dpw.iarts.entity.AvailabilityRequest;
import za.gov.dpw.iarts.entity.IntraRequest;
import za.gov.dpw.iarts.entity.User;
import za.gov.dpw.iarts.exception.ResourceNotFoundException;
import za.gov.dpw.iarts.repository.AvailabilityRequestRepository;
import za.gov.dpw.iarts.repository.IntraRequestRepository;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IntraRequestService {
    private static final List<String> ALLOWED_STATUSES = List.of("SUBMITTED", "ASSIGNED", "IN_PROGRESS", "READY_FOR_DELIVERY", "COMPLETED");

    private final IntraRequestRepository intraRequestRepository;
    private final AvailabilityRequestRepository availabilityRequestRepository;

    public IntraRequest create(User requester, IntraRequestDto dto) {
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
        return intraRequestRepository.save(request);
    }

    public List<IntraRequest> findAll() {
        return intraRequestRepository.findAllByOrderByCreatedAtDesc();
    }

    public List<IntraRequest> findMine(User requester) {
        return intraRequestRepository.findByRequesterOrderByCreatedAtDesc(requester);
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

    public IntraRequest updateStatus(Long id, IntraRequestStatusDto dto) {
        String status = dto.status().toUpperCase();
        if (!ALLOWED_STATUSES.contains(status)) {
            throw new IllegalArgumentException("Unsupported INTRA request status");
        }
        IntraRequest request = intraRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("INTRA request not found"));
        request.setStatus(status);
        return intraRequestRepository.save(request);
    }
}
