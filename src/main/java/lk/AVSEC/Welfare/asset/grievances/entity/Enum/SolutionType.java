package lk.AVSEC.Welfare.asset.grievances.entity.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SolutionType {
    PR("Pending"),
    CL("Close");
    private final String solutionType;
}
