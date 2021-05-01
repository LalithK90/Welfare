package lk.avsec_welfare.asset.other_fund_receiving.entity;

import lk.avsec_welfare.asset.main_account.entity.Enum.OtherFundReceivingType;
import lk.avsec_welfare.util.audit.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OtherFundReceiving extends AuditEntity {

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    private String remark;

   @Enumerated(EnumType.STRING)
    private OtherFundReceivingType otherFundReceivingType;


}
