package lk.AVSEC.Welfare.asset.finance.dao;

import lk.AVSEC.Welfare.asset.finance.entity.AddPaymentType;
import lk.AVSEC.Welfare.asset.finance.entity.AdditionalPayment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddPaymentTypeDao extends JpaRepository<AddPaymentType, Integer> {

}
