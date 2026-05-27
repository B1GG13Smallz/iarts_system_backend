package za.gov.dpw.iarts.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
    private User actor;
    @Column(nullable = false)
    private String action;
    private String entityType;
    private Long entityId;
    @Column(length = 1500)
    private String details;
    private String ipAddress;
}
