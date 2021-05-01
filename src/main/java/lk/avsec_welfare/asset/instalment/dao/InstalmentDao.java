package lk.avsec_welfare.asset.instalment.dao;

import lk.avsec_welfare.asset.employee.entity.Employee;
import lk.avsec_welfare.asset.instalment.entity.Instalment;
import lk.avsec_welfare.asset.instalment.entity.enums.InstalmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface InstalmentDao extends JpaRepository< Instalment, Integer> {

  List< Instalment > findByEmployee(Employee employee);

  List< Instalment > findByInstalmentStatus(InstalmentStatus instalmentStatus);

  List< Instalment > findByCreatedAtIsBetweenAndCreatedBy(LocalDateTime startDateTime, LocalDateTime endDateTime, String username);

  List<Instalment> findByCreatedAtIsBetween(LocalDateTime startDateTime, LocalDateTime endDateTime);
}
