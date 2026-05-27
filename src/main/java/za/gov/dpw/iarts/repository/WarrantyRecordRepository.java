package za.gov.dpw.iarts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.gov.dpw.iarts.entity.WarrantyRecord;

public interface WarrantyRecordRepository extends JpaRepository<WarrantyRecord, Long> {}

