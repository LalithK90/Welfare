package lk.avsec_welfare.asset.finance.installment_type.dao;

import lk.avsec_welfare.asset.finance.installment_type.entity.InstalmentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstalmentTypeDao extends JpaRepository< InstalmentType, Integer> {


}
