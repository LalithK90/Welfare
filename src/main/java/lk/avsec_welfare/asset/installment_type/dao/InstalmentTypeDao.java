package lk.avsec_welfare.asset.installment_type.dao;

import lk.avsec_welfare.asset.installment_type.entity.InstalmentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InstalmentTypeDao extends JpaRepository< InstalmentType, Integer> {
  List<InstalmentType> findByYear(String year);


}
