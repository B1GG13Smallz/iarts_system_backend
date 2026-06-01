package za.gov.dpw.iarts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.gov.dpw.iarts.entity.AssetApproval;
import java.util.Optional;

@Repository
public interface AssetApprovalRepository extends JpaRepository<AssetApproval, Long> {
    Optional<AssetApproval> findFirstByRequestIdOrderByCreatedAtDesc(Long requestId);
}
