package lk.AVSEC.Welfare.asset.kmart.invoice.entity.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum InvoicePrintOrNot {
    PRINTED("Printed"),
    NOT_PRINTED("Not Printed");
    private final String invoicePrintOrNot;
}
