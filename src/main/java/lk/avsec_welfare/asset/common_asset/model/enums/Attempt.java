package lk.avsec_welfare.asset.common_asset.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Attempt {
    FIRST("First"),
    SECOND("Second");

    private final String attempt;
}
