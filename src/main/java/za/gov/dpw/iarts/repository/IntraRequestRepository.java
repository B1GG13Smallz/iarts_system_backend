package za.gov.dpw.iarts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.gov.dpw.iarts.entity.IntraRequest;
import za.gov.dpw.iarts.entity.User;
import java.util.List;
import java.util.Optional;

@Repository
public interface IntraRequestRepository extends JpaRepository<IntraRequest, Long> {
    List<IntraRequest> findAllByOrderByCreatedAtDesc();
    List<IntraRequest> findByRequesterOrderByCreatedAtDesc(User requester);
    Optional<IntraRequest> findFirstByReferenceNumberOrderByCreatedAtDesc(String referenceNumber);
}
