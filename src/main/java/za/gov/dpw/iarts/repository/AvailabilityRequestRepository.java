package za.gov.dpw.iarts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.gov.dpw.iarts.entity.AvailabilityRequest;
import za.gov.dpw.iarts.entity.User;
import java.util.List;
import java.util.Optional;

public interface AvailabilityRequestRepository extends JpaRepository<AvailabilityRequest, Long> {
    List<AvailabilityRequest> findByRequesterOrderByCreatedAtDesc(User requester);
    Optional<AvailabilityRequest> findFirstByRequesterOrderByCreatedAtDesc(User requester);
    Optional<AvailabilityRequest> findFirstByReferenceNumberOrderByCreatedAtDesc(String referenceNumber);
}
