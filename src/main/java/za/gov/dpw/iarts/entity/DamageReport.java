package za.gov.dpw.iarts.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import za.gov.dpw.iarts.constants.DamageStatuses;

@Getter
@Setter
@Entity
@Table(name = "damage_reports")
public class DamageReport extends BaseEntity {
    @ManyToOne(optional = false)
    private Equipment equipment;
    @ManyToOne(optional = false)
    private User reportedBy;
    @Column(nullable = false, length = 1500)
    private String incidentDescription;
    private String status = DamageStatuses.REPORTED;
    private String warrantyReference;
    private String followUpNotes;
}
