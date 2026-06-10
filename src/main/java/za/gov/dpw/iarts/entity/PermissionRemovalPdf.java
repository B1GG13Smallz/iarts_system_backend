package za.gov.dpw.iarts.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "permission_removal_pdfs")
public class PermissionRemovalPdf extends BaseEntity {
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "permission_removal_id", nullable = false, unique = true)
    private PermissionRemoval permissionRemoval;

    @Column(name = "file_name", nullable = false, length = 255)
    private String fileName;

    @Column(name = "content_type", nullable = false, length = 100)
    private String contentType;

    @Lob
    @Column(name = "pdf_data", nullable = false, columnDefinition = "LONGBLOB")
    private byte[] data;
}
