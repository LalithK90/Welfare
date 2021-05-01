package lk.avsec_welfare.asset.working_place.service;

import lk.avsec_welfare.asset.common_asset.model.enums.LiveDead;
import lk.avsec_welfare.asset.working_place.dao.WorkingPlaceDao;
import lk.avsec_welfare.asset.working_place.entity.WorkingPlace;
import lk.avsec_welfare.asset.working_place.entity.enums.WorkingPlaceSection;
import lk.avsec_welfare.util.interfaces.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
// spring transactional annotation need to tell spring to this method work through the project
@CacheConfig( cacheNames = "workingPlace" )
public class WorkingPlaceService implements AbstractService< WorkingPlace, Integer > {

  private final WorkingPlaceDao workingPlaceDao;

  @Autowired
  public WorkingPlaceService(WorkingPlaceDao workingPlaceDao) {
    this.workingPlaceDao = workingPlaceDao;
  }

  @Cacheable
  public List< WorkingPlace > findAll() {
    return workingPlaceDao.findAll().stream().filter(x -> x.getLiveDead().equals(LiveDead.ACTIVE)).collect(Collectors.toList());
  }

  @Cacheable
  public WorkingPlace findById(Integer id) {
    return workingPlaceDao.getOne(id);
  }

  @Caching( evict = {@CacheEvict( value = "workingPlace", allEntries = true )},
      put = {@CachePut( value = "workingPlace", key = "#workingPlace.id" )} )
  @Transactional
  public WorkingPlace persist(WorkingPlace workingPlace) {
    workingPlace.setName(workingPlace.getShortName() + "-" + workingPlace.getAirportType() + "-" + workingPlace.getWorkingPlaceSection() + "-" + workingPlace.getDistrict().getName().toUpperCase());
    if ( workingPlace.getId() == null ) {
      workingPlace.setLiveDead(LiveDead.ACTIVE);
    }
    return workingPlaceDao.save(workingPlace);
  }

  @CacheEvict( allEntries = true )
  public boolean delete(Integer id) {
    WorkingPlace workingPlace = workingPlaceDao.getOne(id);
    workingPlace.setLiveDead(LiveDead.STOP);
    workingPlaceDao.save(workingPlace);
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


  public List< WorkingPlace> findByWorkingPlaceSection(WorkingPlaceSection workingPlaceSection) {
    return workingPlaceDao.findByWorkingPlaceSection(workingPlaceSection);
  }
}
