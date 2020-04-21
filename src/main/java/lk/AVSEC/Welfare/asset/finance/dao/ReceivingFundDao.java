package lk.AVSEC.Welfare.asset.finance.dao;

import lk.AVSEC.Welfare.asset.employee.entity.Employee;
import lk.AVSEC.Welfare.asset.finance.entity.ReceivingFund;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReceivingFundDao extends JpaRepository<ReceivingFund, Integer> {
    // select all from credit where employee=?1 and between created_at=?2, created_at=?3
    List<ReceivingFund> findByEmployeeAndCreatedAtBetween(Employee employee, LocalDateTime from, LocalDateTime to);
}
