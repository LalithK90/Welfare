package lk.avsec_welfare.asset.finance.entity.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FundType {
  INSTALMENTS("Instalments"),
  OTHER_FUN_RE("Other Found Receiving"),
  EXP("Expense"),
  OTHEXP("Other Expense");
  private final String foundReceivedType;
}
