package lk.avsec_welfare.asset.finance.instalment.commonModel.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApproveOrNot {
  APP("Approve"), NAPP("NotApprove");
  private final String approveOrNot;
}
