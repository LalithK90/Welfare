package lk.AVSEC.Welfare.asset.commonAsset.model.Enum;

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
