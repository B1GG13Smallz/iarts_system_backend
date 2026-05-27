package za.gov.dpw.iarts.constants;

import java.util.List;

public final class MovementStatuses {
    public static final String REQUESTED = "REQUESTED";
    public static final String EQUIPMENT_CONFIRMED = "EQUIPMENT_CONFIRMED";
    public static final String ASSET_VERIFIED = "ASSET_VERIFIED";
    public static final String COMPLETED = "COMPLETED";
    public static final String REJECTED = "REJECTED";

    public static final List<String> ALL = List.of(REQUESTED, EQUIPMENT_CONFIRMED, ASSET_VERIFIED, COMPLETED, REJECTED);

    private MovementStatuses() {
    }
}
