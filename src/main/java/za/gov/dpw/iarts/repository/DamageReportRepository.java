package za.gov.dpw.iarts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.gov.dpw.iarts.entity.DamageReport;

public interface DamageReportRepository extends JpaRepository<DamageReport, Long> {}

