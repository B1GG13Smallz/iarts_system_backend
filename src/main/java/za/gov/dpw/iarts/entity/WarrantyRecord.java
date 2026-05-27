package za.gov.dpw.iarts.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import za.gov.dpw.iarts.constants.WarrantyStatuses;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "warranty_records")
public class WarrantyRecord extends BaseEntity {
    @ManyToOne(optional = false)
    private DamageReport damageReport;
    private String supplierReference;
    private LocalDate submittedDate;
    private LocalDate closedDate;
    private String status = WarrantyStatuses.PENDING;
}
