package za.gov.dpw.iarts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.gov.dpw.iarts.entity.RemovalRequest;

@Repository
public interface RemovalRequestRepository extends JpaRepository<RemovalRequest, Long> {}

