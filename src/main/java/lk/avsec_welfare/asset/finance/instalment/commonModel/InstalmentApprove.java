package lk.avsec_welfare.asset.finance.instalment.commonModel;

import lk.avsec_welfare.asset.finance.instalment.commonModel.enums.ApproveOrNot;
import lk.avsec_welfare.asset.finance.instalment.entity.Instalment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Nonnull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstalmentApprove {
private Instalment instalment;
private ApproveOrNot approveOrNot;
}
