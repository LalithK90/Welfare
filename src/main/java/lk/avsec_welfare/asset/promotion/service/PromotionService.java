package lk.avsec_welfare.asset.promotion.service;

import lk.avsec_welfare.asset.common_asset.model.enums.LiveDead;
import lk.avsec_welfare.asset.promotion.dao.PromotionDao;
import lk.avsec_welfare.asset.promotion.entity.Promotion;
import lk.avsec_welfare.util.interfaces.AbstractService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
// spring transactional annotation need to tell spring to this method work through the project
@CacheConfig(cacheNames = "promotion")
public class PromotionService implements AbstractService< Promotion, Integer> {
    private final PromotionDao promotionDao;

    public PromotionService(PromotionDao promotionDao) {
        this.promotionDao = promotionDao;
    }

    public List<Promotion> findAll() {
        return promotionDao.findAll().stream().filter(x->x.getLiveDead().equals(LiveDead.ACTIVE)).collect(Collectors.toList());
    }

    public Promotion findById(Integer id) {
        return promotionDao.getOne(id);
    }

    public Promotion persist(Promotion promotion) {
        if ( promotion.getId() ==null ){
            promotion.setLiveDead(LiveDead.STOP);
        }
        return promotionDao.save(promotion);
    }

    public boolean delete(Integer id) {
        Promotion promotion = promotionDao.getOne(id);
        promotion.setLiveDead(LiveDead.STOP);
        promotionDao.save(promotion);
        return false;
    }

    public List<Promotion> search(Promotion promotion) {
        return null;
    }



/*    public List<Dependency> findByProvince(Province province) {
        return dependencyDao.findByProvince(province);
    }*/
}
