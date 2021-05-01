package lk.avsec_welfare.asset.report.model;

import lk.avsec_welfare.asset.employee.entity.Employee;
import lk.avsec_welfare.asset.working_place.entity.enums.WorkingPlaceSection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SectionEmployeeInstalmentAmount {

  private WorkingPlaceSection workingPlaceSection;
  private Employee employee;
  private long instalmentCount;
  private BigDecimal amount;
}
