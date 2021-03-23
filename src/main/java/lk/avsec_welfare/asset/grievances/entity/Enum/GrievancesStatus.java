package lk.avsec_welfare.asset.grievances.entity.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.poi.hssf.util.HSSFColor;

@Getter
@AllArgsConstructor
public enum GrievancesStatus {
    HOSS("Head of Security Services"),
    DHOSS("Deputy Head of Security Services"),
    PRE("President"),
    SCTY("Secretory");

    private final String grievancesStatus;
}
