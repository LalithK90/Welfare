package lk.avsec_welfare.asset.report.model;

import lk.avsec_welfare.asset.main_account.entity.Enum.OtherFundReceivingType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OtherFundReceivingTypeAmount {
private OtherFundReceivingType otherFundReceivingType;
private BigDecimal amount;
private long recordCount;
}
