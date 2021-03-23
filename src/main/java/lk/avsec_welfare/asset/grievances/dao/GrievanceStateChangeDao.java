package lk.avsec_welfare.asset.grievances.dao;

import lk.avsec_welfare.asset.grievances.entity.GrievanceStateChange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GrievanceStateChangeDao extends JpaRepository< GrievanceStateChange,Integer> {
}
