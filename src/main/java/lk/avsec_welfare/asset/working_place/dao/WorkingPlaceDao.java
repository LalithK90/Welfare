package lk.avsec_welfare.asset.working_place.dao;

import lk.avsec_welfare.asset.working_place.entity.WorkingPlace;
import lk.avsec_welfare.asset.working_place.entity.enums.WorkingPlaceSection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkingPlaceDao extends JpaRepository< WorkingPlace, Integer > {

  List< WorkingPlace > findByWorkingPlaceSection(WorkingPlaceSection workingPlaceSection);
}
