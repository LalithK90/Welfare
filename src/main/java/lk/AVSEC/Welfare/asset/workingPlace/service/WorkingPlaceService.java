package lk.AVSEC.Welfare.asset.workingPlace.service;

import lk.AVSEC.Welfare.asset.workingPlace.dao.WorkingPlaceDao;
import lk.AVSEC.Welfare.asset.workingPlace.entity.WorkingPlace;
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
@CacheConfig( cacheNames = "workingPlace" )
public class WorkingPlaceService implements AbstractService<WorkingPlace, Integer > {

    private final WorkingPlaceDao workingPlaceDao;

    @Autowired
    public WorkingPlaceService(WorkingPlaceDao workingPlaceDao) {
        this.workingPlaceDao = workingPlaceDao;
    }

    @Cacheable
    public List< WorkingPlace > findAll() {
        return workingPlaceDao.findAll();
    }

    @Cacheable
    public WorkingPlace findById(Integer id) {
        return workingPlaceDao.getOne(id);
    }

    @Caching( evict = {@CacheEvict( value = "workingPlace", allEntries = true )},
            put = {@CachePut( value = "workingPlace", key = "#workingPlace.id" )} )
    @Transactional
    public WorkingPlace persist(WorkingPlace workingPlace) {
        return workingPlaceDao.save(workingPlace);
    }

    @CacheEvict( allEntries = true )
    public boolean delete(Integer id) {
        workingPlaceDao.deleteById(id);
        return false;
    }

    @Cacheable
    public List< WorkingPlace > search(WorkingPlace workingPlace) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example< WorkingPlace > workingPlaceExample = Example.of(workingPlace, matcher);
        return workingPlaceDao.findAll(workingPlaceExample);
    }

    public boolean isWorkingPlacePresent(WorkingPlace workingPlace) {
        return workingPlaceDao.equals(workingPlace);
    }


}
