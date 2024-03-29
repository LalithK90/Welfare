package lk.avsec_welfare.asset.instalment.commonModel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class YearAndPaidAmount {
  private String year;
  private BigDecimal yearAmount;
  private BigDecimal paidAmount;
  private BigDecimal yearMandatoryAmount;
  private BigDecimal paidMandatoryAmount;
  private BigDecimal yearOptionalAmount;
  private BigDecimal paidOptionalAmount;
}
