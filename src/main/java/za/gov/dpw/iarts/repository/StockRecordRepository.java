package za.gov.dpw.iarts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.gov.dpw.iarts.entity.StockRecord;
import java.util.List;

public interface StockRecordRepository extends JpaRepository<StockRecord, Long> {
    List<StockRecord> findByStatus(String status);
}

