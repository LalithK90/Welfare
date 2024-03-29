package lk.avsec_welfare.asset.qualification.service;

import lk.avsec_welfare.asset.common_asset.model.enums.LiveDead;
import lk.avsec_welfare.asset.qualification.dao.QualificationDao;
import lk.avsec_welfare.asset.qualification.entity.Qualification;
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
@CacheConfig( cacheNames = "qualification" )
public class QualificationService implements AbstractService< Qualification, Integer > {

  private final QualificationDao qualificationDao;

  @Autowired
  public QualificationService(QualificationDao qualificationDao) {

    this.qualificationDao = qualificationDao;
  }

  @Cacheable
  public List< Qualification > findAll() {
    return qualificationDao.findAll().stream().filter(x -> x.getLiveDead().equals(LiveDead.ACTIVE)).collect(Collectors.toList());
  }

  @Cacheable
  public Qualification findById(Integer id) {
    return qualificationDao.getOne(id);
  }

  @Caching( evict = {@CacheEvict( value = "qualification", allEntries = true )},
      put = {@CachePut( value = "qualification", key = "#qualification.id" )} )
  @Transactional
  public Qualification persist(Qualification qualification) {
    if ( qualification.getId() == null ) {
      qualification.setLiveDead(LiveDead.ACTIVE);
    }
    return qualificationDao.save(qualification);
  }

  @CacheEvict( allEntries = true )
  public boolean delete(Integer id) {
    Qualification qualification = qualificationDao.getOne(id);
    qualification.setLiveDead(LiveDead.STOP);
    qualificationDao.save(qualification);
    return false;
  }

  @Cacheable
  public List< Qualification > search(Qualification qualification) {
    ExampleMatcher matcher = ExampleMatcher
        .matching()
        .withIgnoreCase()
        .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
    Example< Qualification > qualificationExample = Example.of(qualification, matcher);
    return qualificationDao.findAll(qualificationExample);
  }

  public boolean isQualificationPresent(Qualification qualification) {
    return qualificationDao.equals(qualification);
  }


/*    public List<Qualification> findByProvince(Province province) {
        return qualificationDao.findByProvince(province);
    }*/
}
