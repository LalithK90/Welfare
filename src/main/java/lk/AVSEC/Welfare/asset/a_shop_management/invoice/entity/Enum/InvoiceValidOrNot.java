package lk.AVSEC.Welfare.asset.a_shop_management.invoice.entity.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum InvoiceValidOrNot {
    VALID("Valid"),
    NOTVALID("No Valid");
    private final String invoiceValidOrNot;
}
