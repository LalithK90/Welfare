package lk.AVSEC.Welfare.asset.discountRatio.dao;


import lk.AVSEC.Welfare.asset.discountRatio.entity.DiscountRatio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscountRatioDao extends JpaRepository<DiscountRatio, Integer > {
}
