package za.gov.dpw.iarts.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "permission_removals")
public class PermissionRemoval extends BaseEntity {
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_id", nullable = false)
    private User createdBy;

    @Column(name = "official_name", nullable = false, length = 255)
    private String officialName;

    @Column(name = "unit_directorate_branch", length = 255)
    private String unitDirectorateBranch;

    @Column(name = "telephone_number", length = 80)
    private String telephoneNumber;

    @Column(name = "identity_or_persal_number", length = 120)
    private String identityOrPersalNumber;

    @Column(name = "removal_reason", length = 2000)
    private String removalReason;

    @Column(name = "official_signature_file_name", length = 255)
    private String officialSignatureFileName;

    @Column(name = "official_signature_content_type", length = 100)
    private String officialSignatureContentType;

    @Lob
    @Column(name = "official_signature_data", columnDefinition = "LONGBLOB")
    private byte[] officialSignatureData;

    @Column(name = "equipment_description", nullable = false, length = 2000)
    private String equipmentDescription;

    @Column(name = "bar_code", nullable = false, length = 150)
    private String barCode;

    @Column(name = "serial_number", nullable = false, length = 150)
    private String serialNumber;

    @Column(name = "current_location", length = 500)
    private String currentLocation;

    @Column(name = "period", length = 500)
    private String period;

    @Column(name = "new_location", length = 500)
    private String newLocation;

    @Column(name = "ict_signature_file_name", length = 255)
    private String ictSignatureFileName;

    @Column(name = "ict_signature_content_type", length = 100)
    private String ictSignatureContentType;

    @Lob
    @Column(name = "ict_signature_data", columnDefinition = "LONGBLOB")
    private byte[] ictSignatureData;

    @Column(name = "ict_date")
    private LocalDate ictDate;

    @Column(name = "mam_signature_file_name", length = 255)
    private String mamSignatureFileName;

    @Column(name = "mam_signature_content_type", length = 100)
    private String mamSignatureContentType;

    @Lob
    @Column(name = "mam_signature_data", columnDefinition = "LONGBLOB")
    private byte[] mamSignatureData;

    @Column(name = "mam_date")
    private LocalDate mamDate;

    @Column(name = "security_signature_file_name", length = 255)
    private String securitySignatureFileName;

    @Column(name = "security_signature_content_type", length = 100)
    private String securitySignatureContentType;

    @Lob
    @Column(name = "security_signature_data", columnDefinition = "LONGBLOB")
    private byte[] securitySignatureData;

    @Column(name = "security_date")
    private LocalDate securityDate;

    @Column(name = "workflow_status", nullable = false, length = 80)
    private String workflowStatus;
}
