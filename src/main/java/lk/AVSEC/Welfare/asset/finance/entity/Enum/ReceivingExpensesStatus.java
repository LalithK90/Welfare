package lk.AVSEC.Welfare.asset.finance.entity.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReceivingExpensesStatus {
    ACV("Approved"),
    PEN("Pending"),
    REG("Reject"),
    CAN("Cancel");

    private final String status;
}
