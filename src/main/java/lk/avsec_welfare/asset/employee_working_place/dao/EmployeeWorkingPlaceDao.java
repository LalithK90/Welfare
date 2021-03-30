package lk.avsec_welfare.asset.employee_working_place.dao;


import lk.avsec_welfare.asset.employee.entity.Employee;
import lk.avsec_welfare.asset.employee_working_place.entity.EmployeeWorkingPlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeWorkingPlaceDao extends JpaRepository< EmployeeWorkingPlace, Integer> {
    List<EmployeeWorkingPlace> findByEmployee(Employee employee);
}
