package lk.avsec_welfare.asset.grievances.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SolutionType {
    PR("Pending"),
    PRO("Progress"),
    CL("Close");
    private final String solutionType;
}
