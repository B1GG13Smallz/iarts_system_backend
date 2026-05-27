package za.gov.dpw.iarts.constants;

import java.util.List;

public final class AssetTypes {
    public static final String LAPTOP = "LAPTOP";
    public static final String DESKTOP = "DESKTOP";
    public static final String MONITOR = "MONITOR";
    public static final String PRINTER = "PRINTER";
    public static final String SCANNER = "SCANNER";
    public static final String NETWORK_DEVICE = "NETWORK_DEVICE";
    public static final String ACCESSORY = "ACCESSORY";
    public static final String OTHER = "OTHER";

    public static final List<String> ALL = List.of(LAPTOP, DESKTOP, MONITOR, PRINTER, SCANNER, NETWORK_DEVICE, ACCESSORY, OTHER);

    private AssetTypes() {
    }
}
