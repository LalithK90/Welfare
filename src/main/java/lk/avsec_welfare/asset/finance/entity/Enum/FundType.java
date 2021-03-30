package lk.avsec_welfare.asset.finance.entity.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FundType {
  INSTALMENTS("Instalments"),
  OTHER_FUN_RE("Other Found Receiving"),
  EXP("Expence");
  private final String foundReceivedType;
}
