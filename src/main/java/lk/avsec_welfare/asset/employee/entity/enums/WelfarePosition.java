package lk.avsec_welfare.asset.employee.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum WelfarePosition {
    HOSS(" Head of Security Services"),
    DHOSS(" Deputy Head of Security Services"),
    PRE("President"),
    VPRE(" Vice Preside"),
    SCTY("Secretory"),
    VSCTY("Vice Secretory"),
    TRS("Treasure"),
    AGT("Agent"),
    MBR("Member"),
    OTR("Other");

    private final String welfarePosition;
}
