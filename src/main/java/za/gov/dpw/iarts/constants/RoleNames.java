package za.gov.dpw.iarts.constants;

import java.util.List;

public final class RoleNames {
    public static final String END_USER = "END_USER";
    public static final String CONTRACT_EMPLOYEE = "CONTRACT_EMPLOYEE";
    public static final String INTERN = "INTERN";
    public static final String ICT_STOREROOM = "ICT_STOREROOM";
    public static final String TECHNICIAN = "TECHNICIAN";
    public static final String ASSET_MANAGEMENT = "ASSET_MANAGEMENT";
    public static final String SECURITY = "SECURITY";
    public static final String ICT_MANAGEMENT = "ICT_MANAGEMENT";
    public static final String APPLICATIONS_TEAM = "APPLICATIONS_TEAM";
    public static final String DBA_TEAM = "DBA_TEAM";
    public static final String AUDITOR = "AUDITOR";
    public static final String ADMIN = "ADMIN";

    public static final List<String> ALL = List.of(END_USER, CONTRACT_EMPLOYEE, INTERN, ICT_STOREROOM, TECHNICIAN,
            ASSET_MANAGEMENT, SECURITY, ICT_MANAGEMENT, APPLICATIONS_TEAM, DBA_TEAM, AUDITOR, ADMIN);

    private RoleNames() {
    }
}
