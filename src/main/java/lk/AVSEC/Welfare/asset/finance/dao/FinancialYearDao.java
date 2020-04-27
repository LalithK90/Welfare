package lk.AVSEC.Welfare.asset.finance.dao;

import lk.AVSEC.Welfare.asset.finance.entity.FinancialYear;
import lk.AVSEC.Welfare.asset.finance.entity.ReceivingFund;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinancialYearDao extends JpaRepository<FinancialYear, Integer> {

}
