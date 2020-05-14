package lk.AVSEC.Welfare.asset.kmart.invoice.entity.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum InvoiceState {
    PAID("Paid"),
    CANCELLED("Cancelled");

    private final String invoiceState;
}
