package lk.avsec_welfare.asset.working_place.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ShortName {

    BIA("Bandaranayake International Airport"),
    MRIA("Mattala Rajapaksha International Airport"),
    JIA("Jaffna International Airport"),
    RMA("Colombo Airport - Ratmalana"),
    BDA("Batticaloa Airport");


    private final String shortName;

}
