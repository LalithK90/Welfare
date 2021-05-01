package lk.avsec_welfare.asset.report.model;

import lk.avsec_welfare.asset.employee.entity.Employee;
import lk.avsec_welfare.asset.offence.entity.Offence;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OffenceEmployee {
private Offence offence;
private List< Employee > employees;
}
