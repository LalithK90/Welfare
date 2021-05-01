package lk.avsec_welfare.asset.other_fund_receiving.dao;

import lk.avsec_welfare.asset.other_fund_receiving.entity.OtherFundReceiving;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OtherFundReceivingDao extends JpaRepository< OtherFundReceiving, Integer> {

  List< OtherFundReceiving> findByCreatedAtIsBetween(LocalDateTime startDateTime, LocalDateTime endDateTime);

  List< OtherFundReceiving> countByCreatedAtIsBetweenAndCreatedBy(LocalDateTime startDateTime, LocalDateTime endDateTime, String username);
}
