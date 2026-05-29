package za.gov.dpw.iarts.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
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
    @JoinColumn(name = "equipment_id", nullable = false)
    private Equipment equipment;
    @ManyToOne(optional = false)
    @JoinColumn(name = "reported_by_id", nullable = false)
    private User reportedBy;
    @Column(name = "incident_description", nullable = false, length = 1500)
    private String incidentDescription;
    @Column(name = "status", nullable = false, length = 50)
    private String status = DamageStatuses.REPORTED;
    @Column(name = "warranty_reference", length = 100)
    private String warrantyReference;
    @Column(name = "follow_up_notes", length = 1000)
    private String followUpNotes;
}
