package za.gov.dpw.iarts.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
    private AssetRequest request;
    @ManyToOne(optional = false)
    private User approver;
    @Column(nullable = false)
    private String decision = ApprovalDecisions.PENDING;
    @Column(length = 1000)
    private String comments;
}
