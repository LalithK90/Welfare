package lk.AVSEC.Welfare.asset.finance.entity;

import lk.AVSEC.Welfare.asset.finance.entity.Enum.ExpenseOrReceived;
import lk.AVSEC.Welfare.util.audit.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountRecord extends AuditEntity {

    private String type;

    private String remark;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private ExpenseOrReceived expenseOrReceived;
}
