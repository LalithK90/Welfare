package lk.avsec_welfare.asset.finance.entity;

import lk.avsec_welfare.asset.finance.entity.Enum.OtherFundReceivingType;
import lk.avsec_welfare.util.audit.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

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
    private OtherFundReceivingType fundReceivingType;

   @OneToMany(mappedBy = "otherFundReceiving")
    private List<MainAccount> mainAccounts;

}
