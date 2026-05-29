package za.gov.dpw.iarts.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
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
    @JoinColumn(name = "requester_id", nullable = false)
    private User requester;
    @ManyToOne(optional = false)
    @JoinColumn(name = "equipment_id", nullable = false)
    private Equipment equipment;
    @Column(name = "from_location", length = 255)
    private String fromLocation;
    @Column(name = "to_location", length = 255)
    private String toLocation;
    @ManyToOne
    @JoinColumn(name = "technician_id")
    private User technician;
    @ManyToOne
    @JoinColumn(name = "asset_management_verifier_id")
    private User assetManagementVerifier;
    @Column(name = "client_confirmed", nullable = false)
    private boolean clientConfirmed;
    @Column(name = "remarks", length = 1000)
    private String remarks;
    @Column(name = "status", nullable = false, length = 50)
    private String status = MovementStatuses.REQUESTED;
}
