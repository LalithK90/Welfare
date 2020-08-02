package lk.AVSEC.Welfare.asset.dependent.dao;

import lk.AVSEC.Welfare.asset.dependent.entity.Dependent;
import lk.AVSEC.Welfare.asset.employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DependentDao extends JpaRepository<Dependent, Integer> {
<<<<<<< HEAD
    List<Dependent> findByEmployee(Employee employee);
/*//select * from district where province = ?1
    List<Qualification> findByProvince(Province province);*/

=======
    Dependent findByNic(String nic);
>>>>>>> 991a9fa77174c9052d9120bd05ad1fd6ca31d6e1
}
