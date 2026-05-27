package za.gov.dpw.iarts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.gov.dpw.iarts.entity.AssetRequest;

public interface AssetRequestRepository extends JpaRepository<AssetRequest, Long> {}

