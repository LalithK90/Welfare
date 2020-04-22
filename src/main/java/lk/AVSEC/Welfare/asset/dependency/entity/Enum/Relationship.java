package lk.AVSEC.Welfare.asset.dependency.entity.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Relationship {
    FAR("Father"),
    MOT("Mother"),
    SON("Son"),
    DAU("Daughter"),
    BRO("Brother"),
    SIS("Sister"),
    DIL("Daughter-in-law"),
    SIL("Son-in-law"),
    BIL("Brother-in-law"),
    SSL("Sister-in-law"),
    FIL("Father-in-law"),
    MIL("Mother-in-law"),
    MEP("Nephew"),
    NIE("Niece"),
    GMO("Grandmother"),
    GFO("Grandfather");

    private final String relationship;
}
