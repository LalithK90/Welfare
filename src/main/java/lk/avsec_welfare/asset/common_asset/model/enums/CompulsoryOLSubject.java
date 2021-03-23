package lk.avsec_welfare.asset.common_asset.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CompulsoryOLSubject {
    MATH("Mathematics"),
    SCIEN("Science"),
    SING("Sinhala Language & Literature"),
    ENG("English Language & Literature"),
    REG("Religion"),
    OTH("Other");

    private final String compulsoryOLSubject;
}
