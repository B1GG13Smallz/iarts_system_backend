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
@Table(name = "availability_requests")
public class AvailabilityRequest extends BaseEntity {
    @ManyToOne(optional = false)
    private User requester;

    @Column(nullable = false)
    private String referenceNumber;

    @Column(nullable = false)
    private String equipment;

    @Column(nullable = false)
    private String status = "PENDING";
}
