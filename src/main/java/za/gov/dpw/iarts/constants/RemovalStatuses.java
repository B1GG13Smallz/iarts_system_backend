package za.gov.dpw.iarts.constants;

import java.util.List;

public final class RemovalStatuses {
    public static final String REQUESTED = "REQUESTED";
    public static final String ICT_APPROVED = "ICT_APPROVED";
    public static final String MAM_APPROVED = "MAM_APPROVED";
    public static final String SECURITY_VALIDATED = "SECURITY_VALIDATED";
    public static final String REJECTED = "REJECTED";
    public static final String RETURNED = "RETURNED";

    public static final List<String> ALL = List.of(REQUESTED, ICT_APPROVED, MAM_APPROVED, SECURITY_VALIDATED,
            REJECTED, RETURNED);

    private RemovalStatuses() {
    }
}
