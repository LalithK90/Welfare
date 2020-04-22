package lk.AVSEC.Welfare.asset.employee.dao;

import lk.AVSEC.Welfare.asset.employee.entity.Qualification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QualificationDao extends JpaRepository<Qualification, Integer> {

/*//select * from district where province = ?1
    List<Qualification> findByProvince(Province province);*/

}
