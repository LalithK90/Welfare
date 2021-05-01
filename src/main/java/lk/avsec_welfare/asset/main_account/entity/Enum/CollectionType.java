package lk.avsec_welfare.asset.main_account.entity.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CollectionType {
    OPT("Optional"),
    MON("Mandatory");

    private final String collectionType;
}

