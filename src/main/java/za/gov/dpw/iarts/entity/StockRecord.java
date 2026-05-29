package za.gov.dpw.iarts.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import za.gov.dpw.iarts.constants.StockStatuses;

@Getter
@Setter
@Entity
@Table(name = "stock_records")
public class StockRecord extends BaseEntity {
    @ManyToOne(optional = false)
    @JoinColumn(name = "equipment_id", nullable = false)
    private Equipment equipment;
    @Column(name = "status", nullable = false, length = 50)
    private String status = StockStatuses.AVAILABLE;
    @Column(name = "storeroom_location", length = 255)
    private String storeroomLocation;
    @Column(name = "remarks", length = 1000)
    private String remarks;
}
