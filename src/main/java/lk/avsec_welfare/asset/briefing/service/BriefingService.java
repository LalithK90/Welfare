package lk.avsec_welfare.asset.briefing.service;

import lk.avsec_welfare.asset.briefing.dao.BriefingDao;
import lk.avsec_welfare.asset.briefing.entity.Briefing;
import lk.avsec_welfare.asset.common_asset.model.enums.LiveDead;
import lk.avsec_welfare.util.interfaces.AbstractService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@CacheConfig( cacheNames = "briefing" )
public class BriefingService implements AbstractService< Briefing, Integer > {
  private final BriefingDao briefingDao;

  public BriefingService(BriefingDao briefingDao) {
    this.briefingDao = briefingDao;
  }


  public List< Briefing > findAll() {
    return briefingDao.findAll().stream().filter(x -> x.getLiveDead().equals(LiveDead.ACTIVE)).collect(Collectors.toList());
  }

  public Briefing findById(Integer id) {
    return briefingDao.getOne(id);
  }

  public Briefing persist(Briefing briefing) {
    if ( briefing.getId() == null ) {
      briefing.setLiveDead(LiveDead.ACTIVE);
    }
    return briefingDao.save(briefing);
  }

  public boolean delete(Integer id) {
    Briefing briefing = briefingDao.getOne(id);
    briefing.setLiveDead(LiveDead.STOP);
    briefingDao.save(briefing);
    return false;
  }

  public List< Briefing > search(Briefing briefing) {
    return null;
  }


/*    public List<Briefing> findByProvince(Province province) {
        return briefingDao.findByProvince(province);
    }*/
}
