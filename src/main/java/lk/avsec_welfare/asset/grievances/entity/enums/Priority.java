package lk.avsec_welfare.asset.grievances.entity.enums;

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
