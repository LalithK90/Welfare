package lk.AVSEC.Welfare.asset.finance.dao;

import lk.AVSEC.Welfare.asset.finance.entity.ExpensesFund;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpensesFundDao extends JpaRepository<ExpensesFund,Integer> {
}
