package za.gov.dpw.iarts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.gov.dpw.iarts.entity.Approval;

public interface ApprovalRepository extends JpaRepository<Approval, Long> {}

