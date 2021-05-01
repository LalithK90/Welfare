package lk.avsec_welfare.asset.main_account.dao;

import lk.avsec_welfare.asset.main_account.entity.ExpensesFund;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpensesFundDao extends JpaRepository< ExpensesFund, Integer> {

}
