package lk.avsec_welfare.asset.grievances.service;

import lk.avsec_welfare.asset.grievances.dao.GrievanceStateChangeDao;
import lk.avsec_welfare.asset.grievances.entity.GrievanceStateChange;
import lk.avsec_welfare.util.interfaces.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
// spring transactional annotation need to tell spring to this method work through the project
@CacheConfig(cacheNames = "grievanceStateChangeStateChange")
public class GrievanceStateChangeService implements AbstractService<GrievanceStateChange, Integer> {

    private final GrievanceStateChangeDao grievanceStateChangeStateChangeDao;

    @Autowired
    public GrievanceStateChangeService(GrievanceStateChangeDao grievanceStateChangeStateChangeDao) {

        this.grievanceStateChangeStateChangeDao = grievanceStateChangeStateChangeDao;
    }

    @Cacheable
    public List<GrievanceStateChange> findAll() {

        return grievanceStateChangeStateChangeDao.findAll();
    }

    @Cacheable
    public GrievanceStateChange findById(Integer id) {
        return grievanceStateChangeStateChangeDao.getOne(id);
    }

    @Caching(evict = {@CacheEvict(value = "grievanceStateChangeStateChange", allEntries = true)},
            put = {@CachePut(value = "grievanceStateChangeStateChange", key = "#grievanceStateChange.id")})
    @Transactional
    public GrievanceStateChange persist(GrievanceStateChange grievanceStateChange) {

        return grievanceStateChangeStateChangeDao.save(grievanceStateChange);
    }

    @CacheEvict(allEntries = true)
    public boolean delete(Integer id) {
        grievanceStateChangeStateChangeDao.deleteById(id);
        return false;
    }

    @Cacheable
    public List<GrievanceStateChange> search(GrievanceStateChange grievanceStateChange) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<GrievanceStateChange> grievanceStateChangeStateChangeExample = Example.of(grievanceStateChange, matcher);
        return grievanceStateChangeStateChangeDao.findAll(grievanceStateChangeStateChangeExample);
    }

    public boolean isGrievanceStateChangesPresent(GrievanceStateChange grievanceStateChange) {
        return grievanceStateChangeStateChangeDao.equals(grievanceStateChange);
    }


}
