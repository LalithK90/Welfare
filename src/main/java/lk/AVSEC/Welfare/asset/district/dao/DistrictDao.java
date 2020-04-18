package lk.AVSEC.Welfare.asset.district.dao;

import lk.AVSEC.Welfare.asset.district.entity.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DistrictDao extends JpaRepository <District, Integer>{

}
