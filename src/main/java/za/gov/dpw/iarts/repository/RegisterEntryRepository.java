package za.gov.dpw.iarts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import za.gov.dpw.iarts.entity.RegisterEntry;
import java.util.List;
import java.util.Optional;

@Repository
public interface RegisterEntryRepository extends JpaRepository<RegisterEntry, Long> {
    @Query("""
            select entry
            from RegisterEntry entry
            join fetch entry.createdBy
            where entry.registerType = :registerType
            order by entry.createdAt desc
            """)
    List<RegisterEntry> findByRegisterTypeOrderByCreatedAtDesc(String registerType);

    @Query("""
            select entry
            from RegisterEntry entry
            join fetch entry.createdBy
            where entry.id = :id
            """)
    Optional<RegisterEntry> findWithCreatedByById(Long id);
}
