package lk.avsec_welfare.asset.finance.instalment.dao;

import lk.avsec_welfare.asset.employee.entity.Employee;
import lk.avsec_welfare.asset.finance.instalment.entity.Instalment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InstalmentDao extends JpaRepository< Instalment, Integer> {

    List< Instalment> findByEmployee(Employee employee);

}
