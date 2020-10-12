package lk.AVSEC.Welfare.asset.promotion.dao;

import lk.AVSEC.Welfare.asset.promotion.entity.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionDao extends JpaRepository<Promotion, Integer> {

/*//select * from district where province = ?1
    List<Qualification> findByProvince(Province province);*/

}
