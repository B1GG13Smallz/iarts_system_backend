package za.gov.dpw.iarts.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import za.gov.dpw.iarts.dto.AvailabilityRequestDto;
import za.gov.dpw.iarts.dto.AvailabilityReferenceDto;
import za.gov.dpw.iarts.dto.AvailabilityStatusDto;
import za.gov.dpw.iarts.entity.AvailabilityRequest;
import za.gov.dpw.iarts.entity.User;
import za.gov.dpw.iarts.exception.ResourceNotFoundException;
import za.gov.dpw.iarts.repository.AvailabilityRequestRepository;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AvailabilityRequestService {
    private static final String PENDING = "PENDING";
    private static final String AVAILABLE = "AVAILABLE";
    private static final String UNAVAILABLE = "UNAVAILABLE";

    private final AvailabilityRequestRepository availabilityRequestRepository;

    public AvailabilityRequest create(User requester, AvailabilityRequestDto dto) {
        AvailabilityRequest request = new AvailabilityRequest();
        request.setRequester(requester);
        request.setRequesterName(displayName(requester));
        request.setReferenceNumber(clean(dto.referenceNumber()));
        request.setEquipment(dto.equipment());
        request.setRank(dto.rank());
        request.setStatus(PENDING);
        return availabilityRequestRepository.save(request);
    }

    public List<AvailabilityRequest> findAll() {
        return availabilityRequestRepository.findAllByOrderByCreatedAtDesc();
    }

    public AvailabilityRequest latestFor(User requester) {
        return availabilityRequestRepository.findFirstByRequesterOrderByCreatedAtDesc(requester)
                .orElseThrow(() -> new ResourceNotFoundException("Availability request not found"));
    }

    public AvailabilityRequest updateStatus(Long id, AvailabilityStatusDto dto) {
        String status = dto.status().toUpperCase();
        if (!AVAILABLE.equals(status) && !UNAVAILABLE.equals(status) && !PENDING.equals(status)) {
            throw new IllegalArgumentException("Status must be PENDING, AVAILABLE, or UNAVAILABLE");
        }
        AvailabilityRequest request = availabilityRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Availability request not found"));
        request.setStatus(status);
        if (AVAILABLE.equals(status)) {
            request.setDescription(required(dto.description(), "Description"));
            request.setSerialNumber(required(dto.serialNumber(), "Serial number"));
            request.setBarCodeNumber(required(dto.barCodeNumber(), "Bar code number"));
        } else {
            request.setDescription(null);
            request.setSerialNumber(null);
            request.setBarCodeNumber(null);
        }
        return availabilityRequestRepository.save(request);
    }

    public AvailabilityRequest updateReference(User requester, Long id, AvailabilityReferenceDto dto) {
        AvailabilityRequest request = availabilityRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Availability request not found"));

        if (!request.getRequester().getId().equals(requester.getId())) {
            throw new IllegalArgumentException("Availability request does not belong to the signed-in user");
        }

        request.setReferenceNumber(required(dto.referenceNumber(), "Reference number").toUpperCase());
        return availabilityRequestRepository.save(request);
    }

    private String required(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " is required when confirming available equipment");
        }
        return value.trim();
    }

    private String clean(String value) {
        return value == null || value.isBlank() ? "" : value.trim();
    }

    private String displayName(User requester) {
        if (requester.getFullName() != null && !requester.getFullName().isBlank()) {
            return requester.getFullName().trim();
        }
        return requester.getUsername();
    }
}
