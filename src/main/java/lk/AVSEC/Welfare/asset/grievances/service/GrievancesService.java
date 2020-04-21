package lk.AVSEC.Welfare.asset.grievances.service;

import lk.AVSEC.Welfare.asset.grievances.dao.GrievancesDao;
import lk.AVSEC.Welfare.asset.grievances.entity.Grievances;
import lk.AVSEC.Welfare.util.interfaces.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
// spring transactional annotation need to tell spring to this method work through the project
@CacheConfig(cacheNames = "grievances")
public class GrievancesService implements AbstractService<Grievances, Integer> {

    private final GrievancesDao grievancesDao;

    @Autowired
    public GrievancesService(GrievancesDao grievancesDao) {

        this.grievancesDao = grievancesDao;
    }

    @Cacheable
    public List<Grievances> findAll() {

        return grievancesDao.findAll();
    }

    @Cacheable
    public Grievances findById(Integer id) {
        return grievancesDao.getOne(id);
    }

    @Caching(evict = {@CacheEvict(value = "grievances", allEntries = true)},
            put = {@CachePut(value = "grievances", key = "#grievances.id")})
    @Transactional
    public Grievances persist(Grievances grievances) {
        return grievancesDao.save(grievances);
    }

    @CacheEvict(allEntries = true)
    public boolean delete(Integer id) {
        grievancesDao.deleteById(id);
        return false;
    }

    @Cacheable
    public List<Grievances> search(Grievances grievances) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Grievances> grievancesExample = Example.of(grievances, matcher);
        return grievancesDao.findAll(grievancesExample);
    }

    public boolean isGrievancesPresent(Grievances grievances) {
        return grievancesDao.equals(grievances);
    }


/*    public List<Grievances> findByProvince(Province province) {
        return grievancesDao.findByProvince(province);
    }*/
}
