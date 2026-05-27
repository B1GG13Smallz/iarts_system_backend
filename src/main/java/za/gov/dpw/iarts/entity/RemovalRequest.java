package za.gov.dpw.iarts.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import za.gov.dpw.iarts.constants.RemovalStatuses;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "removal_requests")
public class RemovalRequest extends BaseEntity {
    @ManyToOne(optional = false)
    private User requester;
    @ManyToOne(optional = false)
    private Equipment equipment;
    private String reason;
    private LocalDate removalDate;
    private LocalDate expectedReturnDate;
    @ManyToOne
    private User ictApprover;
    @ManyToOne
    private User mamApprover;
    @ManyToOne
    private User securityValidator;
    @Column(length = 1000)
    private String comments;
    private String status = RemovalStatuses.REQUESTED;
}
