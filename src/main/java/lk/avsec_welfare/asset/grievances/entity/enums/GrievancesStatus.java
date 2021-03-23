package lk.avsec_welfare.asset.grievances.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GrievancesStatus {
    HOSS("Head of Security Services"),
    DHOSS("Deputy Head of Security Services"),
    PRE("President"),
    SCTY("Secretory");

    private final String grievancesStatus;
}
