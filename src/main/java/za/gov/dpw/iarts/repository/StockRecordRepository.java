package za.gov.dpw.iarts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.gov.dpw.iarts.entity.Equipment;
import za.gov.dpw.iarts.entity.StockRecord;
import java.util.List;
import java.util.Optional;

@Repository
public interface StockRecordRepository extends JpaRepository<StockRecord, Long> {
    List<StockRecord> findByStatus(String status);
    Optional<StockRecord> findFirstByEquipmentOrderByIdAsc(Equipment equipment);
    List<StockRecord> findByEquipment(Equipment equipment);
    void deleteByEquipment(Equipment equipment);
}
