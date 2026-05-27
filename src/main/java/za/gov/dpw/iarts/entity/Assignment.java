package za.gov.dpw.iarts.entity;

import jakarta.persistence.Entity;
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
    private Equipment equipment;
    @ManyToOne(optional = false)
    private User assignedTo;
    @ManyToOne(optional = false)
    private User issuedBy;
    @ManyToOne
    private AssetRequest request;
    private LocalDate issuedDate;
    private LocalDate returnedDate;
    private String status = AssignmentStatuses.ACTIVE;
}
