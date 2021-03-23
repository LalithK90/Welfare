package lk.avsec_welfare.asset.dependent.dao;

import lk.avsec_welfare.asset.dependent.entity.Dependent;
import lk.avsec_welfare.asset.dependent.entity.DependentEmployee;
import lk.avsec_welfare.asset.employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DependentEmployeeDao extends JpaRepository<DependentEmployee, Integer> {
    List<DependentEmployee> findByEmployeeOne(Employee employee);

    Dependent findByDependent(Dependent dependent);

    DependentEmployee findByDependentAndEmployeeOne(Dependent dependent, Employee employee);


/*//select * from district where province = ?1
    List<Qualification> findByProvince(Province province);*/

}
