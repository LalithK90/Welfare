package lk.avsec_welfare.asset.dependent.entity.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Relationship {
    WIF("Wife"),
    HUS("Husband"),
    FAR("Father"),
    MOT("Mother"),
    FIL("Father-in-law"),
    MIL("Mother-in-law"),
    SON("Son"),
    DAU("Daughter"),
    BRO("Brother"),
    SIS("Sister")
   /* DIL("Daughter-in-law"),
    SIL("Son-in-law"),
    BIL("Brother-in-law"),
    SSL("Sister-in-law"),
    MEP("Nephew"),
    NIE("Niece"),
    GMO("Grandmother"),
    GFO("Grandfather")*/;

    private final String relationship;
}
