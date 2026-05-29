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
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "asset_approvals")
public class AssetApproval extends BaseEntity {
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "request_id", nullable = false)
    private IntraRequest request;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "approved_by_id", nullable = false)
    private User approvedBy;

    @Column(name = "movable_asset_name", nullable = false, length = 255)
    private String movableAssetName;

    @Column(name = "approval_date", nullable = false)
    private LocalDateTime approvalDate;

    @Column(name = "signature_file_name", nullable = false, length = 255)
    private String signatureFileName;

    @Column(name = "signature_content_type", nullable = false, length = 100)
    private String signatureContentType;

    @Lob
    @Column(name = "signature_data", nullable = false, columnDefinition = "LONGBLOB")
    private byte[] signatureData;
}
