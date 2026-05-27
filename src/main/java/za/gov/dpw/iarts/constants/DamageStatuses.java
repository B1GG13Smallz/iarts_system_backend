package za.gov.dpw.iarts.constants;

import java.util.List;

public final class DamageStatuses {
    public static final String REPORTED = "REPORTED";
    public static final String UNDER_REVIEW = "UNDER_REVIEW";
    public static final String WARRANTY_SUBMITTED = "WARRANTY_SUBMITTED";
    public static final String REPAIR_IN_PROGRESS = "REPAIR_IN_PROGRESS";
    public static final String RESOLVED = "RESOLVED";
    public static final String REJECTED = "REJECTED";

    public static final List<String> ALL = List.of(REPORTED, UNDER_REVIEW, WARRANTY_SUBMITTED, REPAIR_IN_PROGRESS,
            RESOLVED, REJECTED);

    private DamageStatuses() {
    }
}
