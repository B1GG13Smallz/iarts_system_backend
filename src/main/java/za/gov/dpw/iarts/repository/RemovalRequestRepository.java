package za.gov.dpw.iarts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.gov.dpw.iarts.entity.RemovalRequest;

public interface RemovalRequestRepository extends JpaRepository<RemovalRequest, Long> {}

