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
@Table(name = "availability_requests")
public class AvailabilityRequest extends BaseEntity {
    @ManyToOne(optional = false)
    @JoinColumn(name = "requester_id", nullable = false)
    private User requester;

    @Column(name = "reference_number", nullable = false, length = 100)
    private String referenceNumber;

    @Column(name = "equipment", nullable = false, length = 255)
    private String equipment;

    @Column(name = "description", length = 2000)
    private String description;

    @Column(name = "serial_number", length = 100)
    private String serialNumber;

    @Column(name = "bar_code_number", length = 100)
    private String barCodeNumber;

    @Column(name = "status", nullable = false, length = 50)
    private String status = "PENDING";
}
