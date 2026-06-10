package za.gov.dpw.iarts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.gov.dpw.iarts.entity.PermissionRemovalPdf;
import java.util.Optional;

@Repository
public interface PermissionRemovalPdfRepository extends JpaRepository<PermissionRemovalPdf, Long> {
    Optional<PermissionRemovalPdf> findByPermissionRemovalId(Long permissionRemovalId);
}
