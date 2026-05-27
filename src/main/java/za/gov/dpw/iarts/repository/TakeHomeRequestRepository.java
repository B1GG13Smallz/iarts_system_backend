package za.gov.dpw.iarts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.gov.dpw.iarts.entity.TakeHomeRequest;

public interface TakeHomeRequestRepository extends JpaRepository<TakeHomeRequest, Long> {}

