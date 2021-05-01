package lk.avsec_welfare.asset.other_expence.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OtherExpenseType {
  OCCASIONAL("Occasional"),
  SCHOLAR("Scholar"),
  FAREWELL("Farewell"),
  GET("Get together"),
  SOCIALIST("Social Works"),
  RELIGIONS("Religions");

  private final String otherExpenseType;
}
