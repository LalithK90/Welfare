package lk.AVSEC.Welfare.asset.common_asset.model.Enum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor

public enum Department {
    AM("Airport Management"),
    ANS("Air Navigation Services"),
    ALID("Architecture, Landscaping and Interior Design"),
    CATC("Civil Aviation Training"),
    CEM("Civil Engineering (Maintenance)"),
    CEPD("Civil Engineering (Planning & Designs)"),
    SSMsl("Commercial & Properties"),
    EANE("Electronics and Air Navigation Engineering"),
    EE("Electrical Engineering"),
    FIN("Finance"),
    FR("Fire & Rescue Services"),
    HR("Human Resources"),
    IAQA("Internal Audit & Quality Assurance"),
    IT("Information Technology"),
    LGL("Legal"),
    ME("Mechanical Engineering"),
    PJT("Projects"),
    SS("Security Services"),
    SCM("Supply Chain Management"),
    IISO("Intelligence & Investigation and Security Operation"),
    MCC("Marketing and Corporate Communications"),
    STY("Safety");

    private final String department;

}
