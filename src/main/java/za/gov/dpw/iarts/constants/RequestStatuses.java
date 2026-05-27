package za.gov.dpw.iarts.constants;

import java.util.List;

public final class RequestStatuses {
    public static final String SUBMITTED = "SUBMITTED";
    public static final String QTS_ROUTED = "QTS_ROUTED";
    public static final String STOCK_VERIFICATION = "STOCK_VERIFICATION";
    public static final String APPROVED = "APPROVED";
    public static final String REJECTED = "REJECTED";
    public static final String ISSUED = "ISSUED";
    public static final String CANCELLED = "CANCELLED";

    public static final List<String> ALL = List.of(SUBMITTED, QTS_ROUTED, STOCK_VERIFICATION, APPROVED, REJECTED,
            ISSUED, CANCELLED);

    private RequestStatuses() {
    }
}
