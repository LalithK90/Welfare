package lk.avsec_welfare.asset.censure.dao;

import lk.avsec_welfare.asset.censure.entitiy.Censure;
import lk.avsec_welfare.asset.employee.entity.Employee;
import lk.avsec_welfare.asset.offence.entity.Offence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CensureDao extends JpaRepository< Censure, Integer > {
  List< Censure > findByEmployee(Employee employee);

  List< Censure > findByOffence(Offence offence);

  List< Censure > findByCreatedAtBetweenAndOffence(LocalDateTime form, LocalDateTime to, Offence offence);
}
