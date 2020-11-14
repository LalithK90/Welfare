package lk.AVSEC.Welfare.asset.briefing.service;

import lk.AVSEC.Welfare.asset.briefing.dao.BriefingDao;
import lk.AVSEC.Welfare.asset.briefing.entity.Briefing;
import lk.AVSEC.Welfare.util.interfaces.AbstractService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
// spring transactional annotation need to tell spring to this method work through the project
@CacheConfig(cacheNames = "briefing")
public class BriefingService implements AbstractService<Briefing, Integer> {
    private final BriefingDao briefingDao;
    //id, name, toWhom, notices, priority date
    public BriefingService(BriefingDao briefingDao) {
        this.briefingDao = briefingDao;
    }


    public List<Briefing> findAll() {
        return briefingDao.findAll();
    }

    public Briefing findById(Integer id) {
        return briefingDao.getOne(id);
    }

    public Briefing persist(Briefing briefing) {
        return briefingDao.save(briefing);
    }

    public boolean delete(Integer id) {
        briefingDao.deleteById(id);
        return false;
    }

    public List<Briefing> search(Briefing briefing) {
        return null;
    }


/*    public List<Briefing> findByProvince(Province province) {
        return briefingDao.findByProvince(province);
    }*/
}
