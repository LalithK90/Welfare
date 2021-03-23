package lk.avsec_welfare.asset.district.dao;

import lk.avsec_welfare.asset.common_asset.model.enums.Province;
import lk.avsec_welfare.asset.district.entity.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DistrictDao extends JpaRepository< District, Integer> {
//select * from district where province = ?1
    List<District> findByProvince(Province province);

}
