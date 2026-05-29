package za.gov.dpw.iarts.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import za.gov.dpw.iarts.constants.AssignmentStatuses;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "assignments")
public class Assignment extends BaseEntity {
    @ManyToOne(optional = false)
    @JoinColumn(name = "equipment_id", nullable = false)
    private Equipment equipment;
    @ManyToOne(optional = false)
    @JoinColumn(name = "assigned_to_id", nullable = false)
    private User assignedTo;
    @ManyToOne(optional = false)
    @JoinColumn(name = "issued_by_id", nullable = false)
    private User issuedBy;
    @ManyToOne
    @JoinColumn(name = "request_id")
    private AssetRequest request;
    @Column(name = "issued_date")
    private LocalDate issuedDate;
    @Column(name = "returned_date")
    private LocalDate returnedDate;
    @Column(name = "status", nullable = false, length = 50)
    private String status = AssignmentStatuses.ACTIVE;
}
