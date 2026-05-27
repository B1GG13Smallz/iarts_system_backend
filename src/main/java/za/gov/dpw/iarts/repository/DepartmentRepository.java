package za.gov.dpw.iarts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.gov.dpw.iarts.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long> {}

