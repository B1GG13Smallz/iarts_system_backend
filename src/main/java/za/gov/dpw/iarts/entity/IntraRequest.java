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
@Table(name = "intra_requests")
public class IntraRequest extends BaseEntity {
    @ManyToOne(optional = false)
    private User requester;

    @Column(nullable = false)
    private String referenceNumber;

    private String itpNumber;
    private String orderNumber;

    @Column(nullable = false)
    private String chiefDirectorate;

    @Column(nullable = false)
    private String subDirectorate;

    private String objective;
    private String responsibility;

    @Column(nullable = false)
    private String chiefUser;

    private String callReference;

    private String destinationOwner;
    private String destinationBuilding;
    private String destinationFloor;
    private String destinationOffice;
    private String destinationRegion;
    private String destinationContact;

    @Column(length = 1200)
    private String movementReason;

    @Column(nullable = false)
    private String status = "SUBMITTED";
}
