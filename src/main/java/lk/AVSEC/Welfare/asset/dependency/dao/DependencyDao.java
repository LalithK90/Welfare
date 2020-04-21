package lk.AVSEC.Welfare.asset.dependency.dao;

import lk.AVSEC.Welfare.asset.dependency.entity.Dependency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DependencyDao extends JpaRepository<Dependency, Integer> {

/*//select * from district where province = ?1
    List<Qualification> findByProvince(Province province);*/

}
