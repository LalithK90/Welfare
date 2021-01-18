package lk.AVSEC.Welfare.asset.a_shop_management.discountRatio.entity.Enum;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DiscountRatioStatus {
    ACTIVE("Active"),
    STOP("Stop");

    private final String discountRatioStatus;
}
