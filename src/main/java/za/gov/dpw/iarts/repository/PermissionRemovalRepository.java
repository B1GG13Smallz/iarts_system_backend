package za.gov.dpw.iarts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import za.gov.dpw.iarts.entity.PermissionRemoval;
import java.util.List;

@Repository
public interface PermissionRemovalRepository extends JpaRepository<PermissionRemoval, Long> {
    @Query("""
            select removal
            from PermissionRemoval removal
            where lower(removal.identityOrPersalNumber) like lower(concat('%', :identityOrPersalNumber, '%'))
            order by removal.createdAt desc
            """)
    List<PermissionRemoval> searchByIdentityOrPersalNumber(@Param("identityOrPersalNumber") String identityOrPersalNumber);

    @Query("""
            select removal
            from PermissionRemoval removal
            where lower(removal.identityOrPersalNumber) like lower(concat('%', :identityOrPersalNumber, '%'))
              and removal.workflowStatus = :workflowStatus
            order by removal.createdAt desc
            """)
    List<PermissionRemoval> searchByIdentityOrPersalNumberAndWorkflowStatus(
            @Param("identityOrPersalNumber") String identityOrPersalNumber,
            @Param("workflowStatus") String workflowStatus
    );

    List<PermissionRemoval> findByWorkflowStatusOrderByCreatedAtDesc(String workflowStatus);
}
