package lk.avsec_welfare.asset.finance.main_account.dao;

import lk.avsec_welfare.asset.finance.main_account.entity.Enum.FundType;
import lk.avsec_welfare.asset.finance.main_account.entity.MainAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MainAccountDao extends JpaRepository< MainAccount, Integer> {

  MainAccount findByFundType(FundType fundType);
}
