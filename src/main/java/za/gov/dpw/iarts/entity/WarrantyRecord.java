package za.gov.dpw.iarts.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
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
    @JoinColumn(name = "damage_report_id", nullable = false)
    private DamageReport damageReport;
    @Column(name = "supplier_reference", length = 100)
    private String supplierReference;
    @Column(name = "submitted_date")
    private LocalDate submittedDate;
    @Column(name = "closed_date")
    private LocalDate closedDate;
    @Column(name = "status", nullable = false, length = 50)
    private String status = WarrantyStatuses.PENDING;
}
