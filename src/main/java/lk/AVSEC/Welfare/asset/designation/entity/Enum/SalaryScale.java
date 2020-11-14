package lk.AVSEC.Welfare.asset.designation.entity.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor

public enum SalaryScale {

    ONE("Scale 01"),
    TWO("Scale 02"),
    THR("Scale 03"),
    FOR("Scale 04"),
    FIV("Scale 05"),
    SIX("Scale 06"),
    SEV("Scale 07"),
    EIG("Scale 08"),
    NIN("Scale 09"),
    TEN("Scale 10"),
    ELE("Scale 11"),
    TWL("Scale 12"),
    THN("Scale 13"),
    FON("Scale 14"),
    OTH("Other");


    private final String salaryScale;
}
