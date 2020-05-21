package lk.AVSEC.Welfare.asset.grievances.entity.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.poi.hssf.util.HSSFColor;

@Getter
@AllArgsConstructor
public enum GrievancesStatus {
    INITIAL("Initial State"),
    SE("Secretory"),
    PR("President"),
    CH("Chairman");

    private final String grievancesStatus;
}
