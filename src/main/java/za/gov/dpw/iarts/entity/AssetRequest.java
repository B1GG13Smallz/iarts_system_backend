package za.gov.dpw.iarts.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import za.gov.dpw.iarts.constants.RequestStatuses;

@Getter
@Setter
@Entity
@Table(name = "asset_requests")
public class AssetRequest extends BaseEntity {
    @ManyToOne(optional = false)
    @JoinColumn(name = "requester_id", nullable = false)
    private User requester;
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
    @Column(name = "asset_type", nullable = false, length = 100)
    private String assetType;
    @Column(name = "justification", nullable = false, length = 1200)
    private String justification;
    @Column(name = "status", nullable = false, length = 50)
    private String status = RequestStatuses.SUBMITTED;
    @Column(name = "printer_routed_to_qts", nullable = false)
    private boolean printerRoutedToQts;
    @Column(name = "qts_reference", length = 100)
    private String qtsReference;
    @Column(name = "stock_verification_remarks", length = 500)
    private String stockVerificationRemarks;
}
