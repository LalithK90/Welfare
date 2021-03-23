package lk.avsec_welfare.asset.transfer.entity.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TransferReason {
    Punishment("Punishment"),
    Request("Request"),
    Roster("Roster"),
    Mutual("Mutual"),
    Category("Category");
    private final String solutionType;
}
