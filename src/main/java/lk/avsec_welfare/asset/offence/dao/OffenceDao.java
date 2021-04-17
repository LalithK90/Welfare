package lk.avsec_welfare.asset.offence.dao;


import lk.avsec_welfare.asset.offence.entity.Offence;
import lk.avsec_welfare.asset.offence.entity.enums.OffenceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OffenceDao extends JpaRepository<Offence, Integer> {
  List< Offence> findByOffenceType(OffenceType offenceType);
}
