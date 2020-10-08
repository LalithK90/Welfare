package lk.AVSEC.Welfare.asset.transfers.entity.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TranferReason {
    Punishment("Punishment"),
    Request("Request"),
    Roster("Roster"),
    Mutual("Mutual"),
    Category("Category");
    private final String solutionType;
}
