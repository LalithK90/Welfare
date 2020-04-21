package lk.AVSEC.Welfare.asset.finance.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import lk.AVSEC.Welfare.asset.employee.entity.Employee;
import lk.AVSEC.Welfare.asset.finance.entity.Enum.ReceivingExpensesStatus;
import lk.AVSEC.Welfare.util.audit.AuditEntity;
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
@JsonFilter("Debit")
public class ExpensesFund extends AuditEntity {
    private BigDecimal amount;

    private String type;

    @ManyToOne
    private Employee employee;

    private String remark;

    @Enumerated(EnumType.STRING)
    private ReceivingExpensesStatus receivingExpensesStatus;
}
