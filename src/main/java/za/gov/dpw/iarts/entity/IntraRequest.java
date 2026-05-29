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
@Table(name = "intra_requests")
public class IntraRequest extends BaseEntity {
    @ManyToOne(optional = false)
    @JoinColumn(name = "requester_id", nullable = false)
    private User requester;

    @Column(name = "reference_number", nullable = false, length = 100)
    private String referenceNumber;

    @Column(name = "itp_number", length = 100)
    private String itpNumber;
    @Column(name = "order_number", length = 100)
    private String orderNumber;

    @Column(name = "chief_directorate", nullable = false, length = 255)
    private String chiefDirectorate;

    @Column(name = "sub_directorate", nullable = false, length = 255)
    private String subDirectorate;

    @Column(name = "objective", length = 500)
    private String objective;
    @Column(name = "responsibility", length = 255)
    private String responsibility;

    @Column(name = "chief_user", nullable = false, length = 255)
    private String chiefUser;

    @Column(name = "call_reference", length = 100)
    private String callReference;

    @Column(name = "current_owner", length = 255)
    private String currentOwner;
    @Column(name = "current_building", length = 255)
    private String currentBuilding;
    @Column(name = "current_floor", length = 100)
    private String currentFloor;
    @Column(name = "current_office", length = 100)
    private String currentOffice;
    @Column(name = "current_region", length = 255)
    private String currentRegion;
    @Column(name = "current_contact", length = 100)
    private String currentContact;

    @Column(name = "destination_owner", length = 255)
    private String destinationOwner;
    @Column(name = "destination_building", length = 255)
    private String destinationBuilding;
    @Column(name = "destination_floor", length = 100)
    private String destinationFloor;
    @Column(name = "destination_office", length = 100)
    private String destinationOffice;
    @Column(name = "destination_region", length = 255)
    private String destinationRegion;
    @Column(name = "destination_contact", length = 100)
    private String destinationContact;

    @Column(name = "movement_reason", length = 1200)
    private String movementReason;

    @Column(name = "status", nullable = false, length = 50)
    private String status = "SUBMITTED";
}
