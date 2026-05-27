package za.gov.dpw.iarts.constants;

import java.util.List;

public final class AssignmentStatuses {
    public static final String ACTIVE = "ACTIVE";
    public static final String RETURNED = "RETURNED";
    public static final String TRANSFERRED = "TRANSFERRED";
    public static final String LOST = "LOST";
    public static final String DAMAGED = "DAMAGED";

    public static final List<String> ALL = List.of(ACTIVE, RETURNED, TRANSFERRED, LOST, DAMAGED);

    private AssignmentStatuses() {
    }
}
