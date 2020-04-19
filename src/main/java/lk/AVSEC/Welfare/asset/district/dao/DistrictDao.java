package lk.AVSEC.Welfare.asset.district.dao;

import lk.AVSEC.Welfare.asset.commonAsset.model.Enum.Province;
import lk.AVSEC.Welfare.asset.district.entity.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DistrictDao extends JpaRepository<District, Integer> {
//select * from district where province = ?1
    List<District> findByProvince(Province province);

}
