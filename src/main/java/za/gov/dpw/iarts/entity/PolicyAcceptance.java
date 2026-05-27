package za.gov.dpw.iarts.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "policy_acceptances")
public class PolicyAcceptance extends BaseEntity {
    @ManyToOne(optional = false)
    private User user;
    @ManyToOne(optional = false)
    private Equipment equipment;
    @Column(nullable = false)
    private String policyName;
    @Column(length = 1200)
    private String acceptanceText;
    private LocalDateTime acceptedAt;
}
