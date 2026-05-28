package za.gov.dpw.iarts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.gov.dpw.iarts.entity.IntraRequest;
import za.gov.dpw.iarts.entity.User;
import java.util.List;

public interface IntraRequestRepository extends JpaRepository<IntraRequest, Long> {
    List<IntraRequest> findByRequesterOrderByCreatedAtDesc(User requester);
}
