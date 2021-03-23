package lk.avsec_welfare.asset.common_asset.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StreamLevel {
    OL("Ordinary Level"),
    AL("Advance Level");

    private final String streamLevel;
}
