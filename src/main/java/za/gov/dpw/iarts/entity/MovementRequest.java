package za.gov.dpw.iarts.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import za.gov.dpw.iarts.constants.MovementStatuses;

@Getter
@Setter
@Entity
@Table(name = "movement_requests")
public class MovementRequest extends BaseEntity {
    @ManyToOne(optional = false)
    private User requester;
    @ManyToOne(optional = false)
    private Equipment equipment;
    private String fromLocation;
    private String toLocation;
    @ManyToOne
    private User technician;
    @ManyToOne
    private User assetManagementVerifier;
    private boolean clientConfirmed;
    @Column(length = 1000)
    private String remarks;
    private String status = MovementStatuses.REQUESTED;
}
