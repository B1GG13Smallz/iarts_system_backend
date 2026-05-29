package za.gov.dpw.iarts.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
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
    @JoinColumn(name = "requester_id", nullable = false)
    private User requester;
    @ManyToOne(optional = false)
    @JoinColumn(name = "equipment_id", nullable = false)
    private Equipment equipment;
    @Column(name = "reason", length = 500)
    private String reason;
    @Column(name = "removal_date")
    private LocalDate removalDate;
    @Column(name = "expected_return_date")
    private LocalDate expectedReturnDate;
    @ManyToOne
    @JoinColumn(name = "ict_approver_id")
    private User ictApprover;
    @ManyToOne
    @JoinColumn(name = "mam_approver_id")
    private User mamApprover;
    @ManyToOne
    @JoinColumn(name = "security_validator_id")
    private User securityValidator;
    @Column(name = "comments", length = 1000)
    private String comments;
    @Column(name = "status", nullable = false, length = 50)
    private String status = RemovalStatuses.REQUESTED;
}
