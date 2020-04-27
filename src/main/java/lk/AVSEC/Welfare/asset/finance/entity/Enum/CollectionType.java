package lk.AVSEC.Welfare.asset.finance.entity.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CollectionType {
    OPT("Optional"),
    MEN("Mandatory");

    private final String collectionType;
}

