package lk.AVSEC.Welfare.asset.a_shop_management.invoice.entity.Enum;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentMethod {
    CASH("Cash"),
    CREDIT("Credit card");
    private final String paymentMethod;
}
