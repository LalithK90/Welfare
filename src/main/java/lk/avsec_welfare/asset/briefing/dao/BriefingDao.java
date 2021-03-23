package lk.avsec_welfare.asset.briefing.dao;

import lk.avsec_welfare.asset.briefing.entity.Briefing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BriefingDao extends JpaRepository< Briefing, Integer> {

/*//select * from district where province = ?1
    List<Qualification> findByProvince(Province province);*/
//id, name, toWhom, notices, priority date
}
