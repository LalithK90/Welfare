package lk.AVSEC.Welfare.asset.workingPlace.entity.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AirportType {
    INTL("International"),
    DOMS("Domestic"),
    REGL("Regional"),
    COML("Commercial "),
    MILT("Military"),
    PVTE("Private");

    private final String airportType;
}
