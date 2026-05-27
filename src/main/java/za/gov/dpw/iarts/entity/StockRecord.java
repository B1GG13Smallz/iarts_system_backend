package za.gov.dpw.iarts.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
    private Equipment equipment;
    @Column(nullable = false)
    private String status = StockStatuses.AVAILABLE;
    private String storeroomLocation;
    private String remarks;
}
