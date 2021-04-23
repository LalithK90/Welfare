package lk.avsec_welfare.asset.report.model;

import lk.avsec_welfare.asset.finance.other_expence.entity.enums.OtherExpenseType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OtherExpenseCountAmount {
  private OtherExpenseType otherExpenseType;
  private long recordCounter;
  private BigDecimal amount;
}
