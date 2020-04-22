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
@JsonFilter("Credit")
public class ReceivingFund extends AuditEntity {


    private BigDecimal amount;

    private String type;


    private String remark;

    @Enumerated
    private ReceivingExpensesStatus receivingExpensesStatus;

    @ManyToOne
    private Employee employee;
// amount, type, member, remark, status

}
