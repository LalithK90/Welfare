package lk.avsec_welfare.asset.promotion.service;

import lk.avsec_welfare.asset.promotion.dao.PromotionDao;
import lk.avsec_welfare.asset.promotion.entity.Promotion;
import lk.avsec_welfare.util.interfaces.AbstractService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
// spring transactional annotation need to tell spring to this method work through the project
@CacheConfig(cacheNames = "promotion")
public class PromotionService implements AbstractService< Promotion, Integer> {
    private final PromotionDao promotionDao;

    public PromotionService(PromotionDao promotionDao) {
        this.promotionDao = promotionDao;
    }

    public List<Promotion> findAll() {
        return promotionDao.findAll();
    }

    public Promotion findById(Integer id) {
        return promotionDao.getOne(id);
    }

    public Promotion persist(Promotion promotion) {
        return promotionDao.save(promotion);
    }

    public boolean delete(Integer id) {
        promotionDao.deleteById(id);
        return false;
    }

    public List<Promotion> search(Promotion promotion) {
        return null;
    }



/*    public List<Dependency> findByProvince(Province province) {
        return dependencyDao.findByProvince(province);
    }*/
}
