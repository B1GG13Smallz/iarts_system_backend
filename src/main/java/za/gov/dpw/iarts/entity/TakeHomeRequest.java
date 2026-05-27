package za.gov.dpw.iarts.entity;

import jakarta.persistence.Entity;
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
    private User requester;
    @ManyToOne(optional = false)
    private Equipment equipment;
    private String requesterCategory;
    private LocalDate startDate;
    private LocalDate endDate;
    private String reason;
    @ManyToOne
    private User approver;
    private String status = TakeHomeStatuses.REQUESTED;
}
