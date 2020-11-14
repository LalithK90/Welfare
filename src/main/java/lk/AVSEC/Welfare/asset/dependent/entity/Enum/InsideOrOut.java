package lk.AVSEC.Welfare.asset.dependent.entity.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum InsideOrOut {

    IN("Working on In side"),
    OUT("Not working on in side");

    private final String insideOrOut;
}
