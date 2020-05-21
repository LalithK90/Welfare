package lk.AVSEC.Welfare.asset.finance.dao;

import lk.AVSEC.Welfare.asset.employee.entity.Employee;
import lk.AVSEC.Welfare.asset.finance.entity.Enum.ExpensesStatus;
import lk.AVSEC.Welfare.asset.finance.entity.ExpensesFund;
import lk.AVSEC.Welfare.util.audit.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.math.BigDecimal;

@Repository
public interface ExpensesFundDao extends JpaRepository<ExpensesFund, Integer> {

}
