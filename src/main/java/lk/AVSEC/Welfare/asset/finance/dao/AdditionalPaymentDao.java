package lk.AVSEC.Welfare.asset.finance.dao;

import lk.AVSEC.Welfare.asset.finance.entity.AdditionalPayment;
import lk.AVSEC.Welfare.asset.finance.entity.FinancialYear;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdditionalPaymentDao extends JpaRepository<AdditionalPayment, Integer> {

}
