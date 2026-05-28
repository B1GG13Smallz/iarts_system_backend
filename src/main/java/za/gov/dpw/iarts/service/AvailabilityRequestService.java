package za.gov.dpw.iarts.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import za.gov.dpw.iarts.dto.AvailabilityRequestDto;
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
        request.setReferenceNumber(dto.referenceNumber());
        request.setEquipment(dto.equipment());
        request.setStatus(PENDING);
        return availabilityRequestRepository.save(request);
    }

    public List<AvailabilityRequest> findAll() {
        return availabilityRequestRepository.findAll();
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
        return availabilityRequestRepository.save(request);
    }
}
