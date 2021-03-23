package lk.avsec_welfare.asset.finance.entity;

import lk.avsec_welfare.asset.employee.entity.Employee;
import lk.avsec_welfare.asset.finance.entity.Enum.ExpensesStatus;
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
public class ExpensesFund extends AuditEntity {

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    private String remark;

    @Enumerated(EnumType.STRING)
    private ExpensesStatus expensesStatus;

    @ManyToOne
    private Employee employee;
}
