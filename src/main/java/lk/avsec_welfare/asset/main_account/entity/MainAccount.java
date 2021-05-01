package lk.avsec_welfare.asset.main_account.entity;

import lk.avsec_welfare.asset.main_account.entity.Enum.FundType;
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
public class MainAccount extends AuditEntity {
  @Enumerated( EnumType.STRING )
  private FundType fundType;

  private String remark;

  @Column( nullable = false, precision = 10, scale = 2 )
  private BigDecimal amount;

}
