package za.gov.dpw.iarts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.gov.dpw.iarts.entity.RegisterEntry;

@Repository
public interface RegisterEntryRepository extends JpaRepository<RegisterEntry, Long> {
}
