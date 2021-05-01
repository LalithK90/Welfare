package lk.avsec_welfare.asset.employee.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BoardOfDirectors {

    MBR("Member"),
    OTR("Other");

    private final String boardOfDirectors;
}
