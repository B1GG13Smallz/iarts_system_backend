package za.gov.dpw.iarts.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import za.gov.dpw.iarts.constants.ApprovalDecisions;

@Getter
@Setter
@Entity
@Table(name = "approvals")
public class Approval extends BaseEntity {
    @ManyToOne(optional = false)
    @JoinColumn(name = "request_id", nullable = false)
    private AssetRequest request;
    @ManyToOne(optional = false)
    @JoinColumn(name = "approver_id", nullable = false)
    private User approver;
    @Column(name = "decision", nullable = false, length = 50)
    private String decision = ApprovalDecisions.PENDING;
    @Column(name = "comments", length = 1000)
    private String comments;
}
