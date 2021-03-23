package lk.avsec_welfare.asset.working_place.dao;

import lk.avsec_welfare.asset.working_place.entity.WorkingPlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkingPlaceDao extends JpaRepository < WorkingPlace, Integer>{

}
