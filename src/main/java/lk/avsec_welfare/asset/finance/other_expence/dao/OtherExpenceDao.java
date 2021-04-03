package lk.avsec_welfare.asset.finance.other_expence.dao;

import lk.avsec_welfare.asset.finance.other_expence.entity.OtherExpence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OtherExpenceDao extends JpaRepository< OtherExpence, Integer> {


}
