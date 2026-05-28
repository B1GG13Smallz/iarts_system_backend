package za.gov.dpw.iarts.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import za.gov.dpw.iarts.dto.IntraRequestDto;
import za.gov.dpw.iarts.entity.IntraRequest;
import za.gov.dpw.iarts.entity.User;
import za.gov.dpw.iarts.repository.IntraRequestRepository;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IntraRequestService {
    private final IntraRequestRepository intraRequestRepository;

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
        return intraRequestRepository.findAll();
    }

    public List<IntraRequest> findMine(User requester) {
        return intraRequestRepository.findByRequesterOrderByCreatedAtDesc(requester);
    }
}
