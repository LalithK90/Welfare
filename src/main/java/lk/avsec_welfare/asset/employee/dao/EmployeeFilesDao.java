package lk.avsec_welfare.asset.employee.dao;

import lk.avsec_welfare.asset.employee.entity.Employee;
import lk.avsec_welfare.asset.employee.entity.EmployeeFiles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EmployeeFilesDao extends JpaRepository< EmployeeFiles, Integer > {
    List< EmployeeFiles > findByEmployeeOrderByIdDesc(Employee employee);

    EmployeeFiles findByName(String filename);

    EmployeeFiles findByNewName(String filename);

    EmployeeFiles findByNewId(String filename);

    EmployeeFiles findByEmployee(Employee employee);

    List< Employee > findByCreatedAtBetween(LocalDateTime form, LocalDateTime to);
}
