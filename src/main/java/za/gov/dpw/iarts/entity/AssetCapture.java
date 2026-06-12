package za.gov.dpw.iarts.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import za.gov.dpw.iarts.constants.StockStatuses;

@Getter
@Setter
@Entity
@Table(name = "asset_captures")
public class AssetCapture extends BaseEntity {
    @Column(name = "asset_tag", nullable = false, unique = true, length = 100)
    private String assetTag;

    @Column(name = "serial_number", unique = true, length = 100)
    private String serialNumber;

    @Column(name = "asset_type", nullable = false, length = 100)
    private String assetType;

    @Column(name = "make", length = 100)
    private String make;

    @Column(name = "model", length = 100)
    private String model;

    @Column(name = "location", length = 255)
    private String location;

    @Column(name = "net_track_reference", length = 100)
    private String netTrackReference;

    @Column(name = "laptop_policy_required", nullable = false)
    private boolean laptopPolicyRequired;

    @Column(name = "stock_status", nullable = false, length = 50)
    private String stockStatus = StockStatuses.AVAILABLE;

    @Column(name = "storage_rank", length = 255)
    private String storageRank;

    @Column(name = "remarks", length = 1000)
    private String remarks;
}
