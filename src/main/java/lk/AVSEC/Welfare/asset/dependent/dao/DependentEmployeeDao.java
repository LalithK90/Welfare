package lk.AVSEC.Welfare.asset.dependent.dao;

import lk.AVSEC.Welfare.asset.dependent.entity.Dependent;
import lk.AVSEC.Welfare.asset.dependent.entity.DependentEmployee;
import lk.AVSEC.Welfare.asset.employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DependentEmployeeDao extends JpaRepository<DependentEmployee, Integer> {
    List<DependentEmployee> findByEmployeeOne(Employee employee);

    Dependent findByDependent(Dependent dependent);


/*//select * from district where province = ?1
    List<Qualification> findByProvince(Province province);*/

}
