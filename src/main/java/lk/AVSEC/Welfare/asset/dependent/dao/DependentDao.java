package lk.AVSEC.Welfare.asset.dependent.dao;

import lk.AVSEC.Welfare.asset.dependent.entity.Dependent;
import lk.AVSEC.Welfare.asset.employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DependentDao extends JpaRepository<Dependent, Integer> {
    List<Dependent> findByEmployee(Employee employee);
/*//select * from district where province = ?1
    List<Qualification> findByProvince(Province province);*/

}
