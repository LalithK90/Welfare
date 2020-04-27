package lk.AVSEC.Welfare.asset.finance.entity.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Status {
    PAST("Pass"),
    ACTIVE("Currant"),
    FUTURE("Future");

    private final String status;
}

