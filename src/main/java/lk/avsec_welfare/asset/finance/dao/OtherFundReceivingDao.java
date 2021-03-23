package lk.avsec_welfare.asset.finance.dao;

import lk.avsec_welfare.asset.finance.entity.OtherFundReceiving;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OtherFundReceivingDao extends JpaRepository< OtherFundReceiving, Integer> {


}
