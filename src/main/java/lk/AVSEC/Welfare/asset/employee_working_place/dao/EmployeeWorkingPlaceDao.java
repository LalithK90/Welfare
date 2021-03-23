package lk.AVSEC.Welfare.asset.employee_working_place.dao;


import lk.AVSEC.Welfare.asset.employee_working_place.entity.EmployeeWorkingPlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeWorkingPlaceDao extends JpaRepository<EmployeeWorkingPlace, Integer> {
}
