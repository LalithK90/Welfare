package lk.AVSEC.Welfare.asset.grievances.entity.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.poi.hssf.util.HSSFColor;

@Getter
@AllArgsConstructor
public enum GrievancesStatus {
<<<<<<< HEAD
    INITIAL("Initial State"),
    SE("Secretory"),
    PR("President"),
    CH("Chairman");
=======
    HOSS("Head of Security Services"),
    DHOSS("Deputy Head of Security Services"),
    PRE("President"),
    SCTY("Secretory");
>>>>>>> 991a9fa77174c9052d9120bd05ad1fd6ca31d6e1

    private final String grievancesStatus;
}
