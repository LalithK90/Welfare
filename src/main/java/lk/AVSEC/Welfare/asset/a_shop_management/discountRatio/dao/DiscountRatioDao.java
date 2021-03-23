package lk.AVSEC.Welfare.asset.a_shop_management.discountRatio.dao;


import lk.AVSEC.Welfare.asset.a_shop_management.discountRatio.entity.DiscountRatio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscountRatioDao extends JpaRepository<DiscountRatio, Integer > {
}
