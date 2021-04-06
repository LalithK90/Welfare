package lk.avsec_welfare.asset.finance.instalment.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum InstalmentStatus {
AGC("Agent collected"),
  TA("Treasure Approved");
private final String instalmentStatus;
}
