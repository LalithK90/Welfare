package lk.AVSEC.Welfare.asset.briefing.dao;

import lk.AVSEC.Welfare.asset.briefing.entity.Briefing;
import lk.AVSEC.Welfare.asset.dependency.entity.Dependency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BriefingDao extends JpaRepository<Briefing, Integer> {

/*//select * from district where province = ?1
    List<Qualification> findByProvince(Province province);*/
//id, name, toWhom, notices, priority date
}
