package lk.AVSEC.Welfare.asset.kmart.item.entity.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ItemStatus {

    AVAILABLE("Available"),
    NOT_AVAILABLE("Not Available"),
    ORDERED("Ordered");

    private final String itemStatus;
}
