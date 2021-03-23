package lk.avsec_welfare.asset.qualification.dao;

import lk.avsec_welfare.asset.qualification.entity.Qualification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QualificationDao extends JpaRepository< Qualification, Integer> {

/*//select * from district where province = ?1
    List<Qualification> findByProvince(Province province);*/

}
