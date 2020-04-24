package lk.AVSEC.Welfare.asset.designation.entity.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor

public enum CategoryType {

    UNI("Uniform Staff"),
    CLS("Clerical Staff"),
    MIS("Minor Staff"),
    ESU("Executive Staff Uniform"),
    ESC("Executive Staff Clerical"),
    OTR("Other");


    private final String categoryType;
}
