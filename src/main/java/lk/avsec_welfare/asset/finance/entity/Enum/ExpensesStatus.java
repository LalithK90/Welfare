package lk.avsec_welfare.asset.finance.entity.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExpensesStatus {
    ACV("Approved"),
    PEN("Pending"),
    REG("Reject"),
    CAN("Cancel");

    private final String status;
}
