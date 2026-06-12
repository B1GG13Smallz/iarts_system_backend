package za.gov.dpw.iarts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import za.gov.dpw.iarts.entity.AvailabilityRequest;
import za.gov.dpw.iarts.entity.User;
import java.util.List;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface AvailabilityRequestRepository extends JpaRepository<AvailabilityRequest, Long> {
    @EntityGraph(attributePaths = "requester")
    Optional<AvailabilityRequest> findWithRequesterById(Long id);
    @EntityGraph(attributePaths = "requester")
    List<AvailabilityRequest> findAllByOrderByCreatedAtDesc();
    @EntityGraph(attributePaths = "requester")
    List<AvailabilityRequest> findByRequesterOrderByCreatedAtDesc(User requester);
    @EntityGraph(attributePaths = "requester")
    Optional<AvailabilityRequest> findFirstByRequesterOrderByCreatedAtDesc(User requester);
    @EntityGraph(attributePaths = "requester")
    Optional<AvailabilityRequest> findFirstByReferenceNumberOrderByCreatedAtDesc(String referenceNumber);

    @Modifying
    @Query("""
            delete from AvailabilityRequest request
            where request.status = :status
              and request.createdAt < :cutoff
              and request.referenceNumber is null
            """)
    int deletePendingUnreferencedBefore(String status, LocalDateTime cutoff);
}
