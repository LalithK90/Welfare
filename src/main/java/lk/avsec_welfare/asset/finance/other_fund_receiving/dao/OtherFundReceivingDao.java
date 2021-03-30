package lk.avsec_welfare.asset.finance.other_fund_receiving.dao;

import lk.avsec_welfare.asset.finance.other_fund_receiving.entity.OtherFundReceiving;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OtherFundReceivingDao extends JpaRepository< OtherFundReceiving, Integer> {


}
