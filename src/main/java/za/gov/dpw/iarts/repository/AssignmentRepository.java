package za.gov.dpw.iarts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.gov.dpw.iarts.entity.Assignment;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {}

