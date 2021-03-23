package lk.avsec_welfare.asset.finance.entity.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OtherFundReceivingType {
    DO("Donation"),
    BI("Bank Interest"),
    SI("Shop Income");

    private final String otherFundReceivingType;
}

