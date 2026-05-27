package za.gov.dpw.iarts.constants;

import java.util.List;

public final class ApprovalDecisions {
    public static final String PENDING = "PENDING";
    public static final String APPROVED = "APPROVED";
    public static final String REJECTED = "REJECTED";

    public static final List<String> ALL = List.of(PENDING, APPROVED, REJECTED);

    private ApprovalDecisions() {
    }
}
