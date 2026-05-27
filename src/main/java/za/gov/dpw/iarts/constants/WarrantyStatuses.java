package za.gov.dpw.iarts.constants;

import java.util.List;

public final class WarrantyStatuses {
    public static final String NOT_APPLICABLE = "NOT_APPLICABLE";
    public static final String PENDING = "PENDING";
    public static final String SUBMITTED = "SUBMITTED";
    public static final String APPROVED = "APPROVED";
    public static final String REJECTED = "REJECTED";
    public static final String CLOSED = "CLOSED";

    public static final List<String> ALL = List.of(NOT_APPLICABLE, PENDING, SUBMITTED, APPROVED, REJECTED, CLOSED);

    private WarrantyStatuses() {
    }
}
