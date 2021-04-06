package lk.avsec_welfare.asset.finance.instalment.commonModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstalmentTreasure {
List<InstalmentApprove> instalmentApproves;
private BigDecimal total;
}
