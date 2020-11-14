package lk.AVSEC.Welfare.asset.grievances.entity.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Priority {

    HIG("Immediate"),
    NOM("Normal"),
    LOW("Medium");

    private final String priority;
}
