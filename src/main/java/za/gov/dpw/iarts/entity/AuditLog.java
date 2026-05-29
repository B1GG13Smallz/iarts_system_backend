package za.gov.dpw.iarts.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "audit_logs")
public class AuditLog extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "actor_id")
    private User actor;
    @Column(name = "action", nullable = false, length = 100)
    private String action;
    @Column(name = "entity_type", length = 100)
    private String entityType;
    @Column(name = "entity_id")
    private Long entityId;
    @Column(name = "details", length = 1500)
    private String details;
    @Column(name = "ip_address", length = 100)
    private String ipAddress;
}
