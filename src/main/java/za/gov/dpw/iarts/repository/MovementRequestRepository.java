package za.gov.dpw.iarts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.gov.dpw.iarts.entity.MovementRequest;

public interface MovementRequestRepository extends JpaRepository<MovementRequest, Long> {}

