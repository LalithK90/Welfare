package lk.AVSEC.Welfare.asset.grievances.dao;

import lk.AVSEC.Welfare.asset.grievances.entity.GrievanceStateChange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GrievanceStateChangeDao extends JpaRepository<GrievanceStateChange,Integer> {
}
