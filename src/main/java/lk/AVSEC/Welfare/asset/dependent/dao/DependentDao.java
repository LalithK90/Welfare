package lk.AVSEC.Welfare.asset.dependent.dao;

import lk.AVSEC.Welfare.asset.dependent.entity.Dependent;
import lk.AVSEC.Welfare.asset.employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DependentDao extends JpaRepository<Dependent, Integer> {
    Dependent findByNic(String nic);
}
