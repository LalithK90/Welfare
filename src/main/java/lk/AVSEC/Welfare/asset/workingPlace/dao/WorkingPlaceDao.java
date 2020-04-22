package lk.AVSEC.Welfare.asset.workingPlace.dao;

import lk.AVSEC.Welfare.asset.district.entity.District;
import lk.AVSEC.Welfare.asset.workingPlace.entity.WorkingPlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkingPlaceDao extends JpaRepository <WorkingPlace, Integer>{

}
