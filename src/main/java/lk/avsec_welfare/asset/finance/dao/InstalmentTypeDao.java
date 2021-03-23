package lk.avsec_welfare.asset.finance.dao;

import lk.avsec_welfare.asset.finance.entity.InstalmentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstalmentTypeDao extends JpaRepository< InstalmentType, Integer> {


}
