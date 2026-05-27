package za.gov.dpw.iarts.constants;

import java.util.List;

public final class StockStatuses {
    public static final String AVAILABLE = "AVAILABLE";
    public static final String RESERVED = "RESERVED";
    public static final String ISSUED = "ISSUED";
    public static final String DAMAGED = "DAMAGED";
    public static final String UNDER_WARRANTY = "UNDER_WARRANTY";
    public static final String RETIRED = "RETIRED";

    public static final List<String> ALL = List.of(AVAILABLE, RESERVED, ISSUED, DAMAGED, UNDER_WARRANTY, RETIRED);

    private StockStatuses() {
    }
}
