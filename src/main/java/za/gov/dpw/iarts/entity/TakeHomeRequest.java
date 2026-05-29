package za.gov.dpw.iarts.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import za.gov.dpw.iarts.constants.TakeHomeStatuses;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "take_home_requests")
public class TakeHomeRequest extends BaseEntity {
    @ManyToOne(optional = false)
    @JoinColumn(name = "requester_id", nullable = false)
    private User requester;
    @ManyToOne(optional = false)
    @JoinColumn(name = "equipment_id", nullable = false)
    private Equipment equipment;
    @Column(name = "requester_category", length = 100)
    private String requesterCategory;
    @Column(name = "start_date")
    private LocalDate startDate;
    @Column(name = "end_date")
    private LocalDate endDate;
    @Column(name = "reason", length = 500)
    private String reason;
    @ManyToOne
    @JoinColumn(name = "approver_id")
    private User approver;
    @Column(name = "status", nullable = false, length = 50)
    private String status = TakeHomeStatuses.REQUESTED;
}
