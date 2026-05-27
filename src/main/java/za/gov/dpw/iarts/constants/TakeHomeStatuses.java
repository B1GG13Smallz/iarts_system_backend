package za.gov.dpw.iarts.constants;

import java.util.List;

public final class TakeHomeStatuses {
    public static final String REQUESTED = "REQUESTED";
    public static final String APPROVED = "APPROVED";
    public static final String REJECTED = "REJECTED";
    public static final String ACTIVE = "ACTIVE";
    public static final String RETURNED = "RETURNED";
    public static final String EXPIRED = "EXPIRED";

    public static final List<String> ALL = List.of(REQUESTED, APPROVED, REJECTED, ACTIVE, RETURNED, EXPIRED);

    private TakeHomeStatuses() {
    }
}
