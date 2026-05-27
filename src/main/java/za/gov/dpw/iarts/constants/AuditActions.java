package za.gov.dpw.iarts.constants;

import java.util.List;

public final class AuditActions {
    public static final String LOGIN = "LOGIN";
    public static final String REQUEST_CREATED = "REQUEST_CREATED";
    public static final String REQUEST_APPROVED = "REQUEST_APPROVED";
    public static final String REQUEST_REJECTED = "REQUEST_REJECTED";
    public static final String STOCK_VERIFIED = "STOCK_VERIFIED";
    public static final String ASSET_ASSIGNED = "ASSET_ASSIGNED";
    public static final String MOVEMENT_CREATED = "MOVEMENT_CREATED";
    public static final String REMOVAL_CREATED = "REMOVAL_CREATED";
    public static final String DAMAGE_REPORTED = "DAMAGE_REPORTED";
    public static final String POLICY_ACCEPTED = "POLICY_ACCEPTED";
    public static final String USER_CREATED = "USER_CREATED";
    public static final String STATUS_CHANGED = "STATUS_CHANGED";

    public static final List<String> ALL = List.of(LOGIN, REQUEST_CREATED, REQUEST_APPROVED, REQUEST_REJECTED,
            STOCK_VERIFIED, ASSET_ASSIGNED, MOVEMENT_CREATED, REMOVAL_CREATED, DAMAGE_REPORTED, POLICY_ACCEPTED,
            USER_CREATED, STATUS_CHANGED);

    private AuditActions() {
    }
}
