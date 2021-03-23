package lk.avsec_welfare.asset.finance.entity.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExpenseOrReceived {
    RECEIVED("Received"),
    EXPENSES("Expenses");

    private final String expenseOrReceived;
}
