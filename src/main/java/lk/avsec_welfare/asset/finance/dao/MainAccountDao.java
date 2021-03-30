package lk.avsec_welfare.asset.finance.dao;

import lk.avsec_welfare.asset.finance.entity.Enum.FundType;
import lk.avsec_welfare.asset.finance.entity.MainAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MainAccountDao extends JpaRepository< MainAccount, Integer> {

  MainAccount findByFundType(FundType fundType);
}
