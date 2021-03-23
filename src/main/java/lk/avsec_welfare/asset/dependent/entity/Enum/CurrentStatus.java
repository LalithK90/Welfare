package lk.avsec_welfare.asset.dependent.entity.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CurrentStatus {
    ACT("Active"),
    DAT("Deactivate"),
    CNL("Cancel"),
    BET("Benefit Obtain"),
    OTR("Other");


    private final String currentStatus;
}
