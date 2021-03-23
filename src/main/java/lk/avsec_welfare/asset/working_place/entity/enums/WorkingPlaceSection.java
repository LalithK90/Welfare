package lk.avsec_welfare.asset.working_place.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum WorkingPlaceSection {
    A("A - Group"),
    B("B - Group"),
    C("C - Group"),
    D("D - Group"),
    AD("Admin"),
    CT("CCTV"),
    CS("Civil Staff");


    private final String workingPlaceSection;
}
