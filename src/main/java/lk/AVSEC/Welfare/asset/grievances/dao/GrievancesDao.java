package lk.AVSEC.Welfare.asset.grievances.dao;

import lk.AVSEC.Welfare.asset.grievances.entity.Grievances;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GrievancesDao extends JpaRepository<Grievances, Integer> {

/*//select * from district where province = ?1
    List<Qualification> findByProvince(Province province);*/

}
