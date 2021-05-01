package lk.avsec_welfare.asset.report.model;

import lk.avsec_welfare.asset.employee.entity.Employee;
import lk.avsec_welfare.asset.working_place.entity.WorkingPlace;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstituteEmployee {
private WorkingPlace workingPlace;
private List< Employee > employees;
}
