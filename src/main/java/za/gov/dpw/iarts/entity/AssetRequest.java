package za.gov.dpw.iarts.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
    private User requester;
    @ManyToOne
    private Department department;
    @Column(nullable = false)
    private String assetType;
    @Column(nullable = false, length = 1200)
    private String justification;
    @Column(nullable = false)
    private String status = RequestStatuses.SUBMITTED;
    private boolean printerRoutedToQts;
    private String qtsReference;
    private String stockVerificationRemarks;
}
