package lk.avsec_welfare.asset.employee.dao;

import lk.avsec_welfare.asset.employee.entity.Employee;
import lk.avsec_welfare.asset.employee.entity.enums.WelfarePosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface EmployeeDao extends JpaRepository< Employee, Integer > {
  Employee findFirstByOrderByIdDesc();

  List< Employee > findAllByOrderByIdDesc();

  Employee findByNic(String nic);

  Employee findByEpf(String epf);

  List< Employee > findByCreatedAtBetween(LocalDateTime form, LocalDateTime to);

  Employee findByWelfarePosition(WelfarePosition welfarePosition);
}
