package lk.avsec_welfare.asset.instalment.commonModel;

import lk.avsec_welfare.asset.employee.entity.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstalmentTreasure {
private  List<InstalmentApprove> instalmentApproves;
private BigDecimal total;
private Employee employee;
}
