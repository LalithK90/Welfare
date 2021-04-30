package lk.avsec_welfare.asset.report.model;

import lk.avsec_welfare.asset.employee.entity.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgentTotalAmount {
  private Employee employee;
  private long count;
  private BigDecimal amount;
}
