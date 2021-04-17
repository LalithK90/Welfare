package lk.avsec_welfare.asset.dependent.service;

import lk.avsec_welfare.asset.common_asset.model.enums.LiveDead;
import lk.avsec_welfare.asset.dependent.dao.DependentDao;
import lk.avsec_welfare.asset.dependent.entity.Dependent;
import lk.avsec_welfare.asset.dependent.entity.Enum.CurrentStatus;
import lk.avsec_welfare.asset.employee.entity.Employee;
import lk.avsec_welfare.util.interfaces.AbstractService;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
// spring transactional annotation need to tell spring to this method work through the project
@CacheConfig( cacheNames = "dependent" )
public class DependentService implements AbstractService< Dependent, Integer > {
  private final DependentDao dependentDao;

  public DependentService(DependentDao dependentDao) {
    this.dependentDao = dependentDao;
  }


  public List< Dependent > findAll() {
    return dependentDao.findAll()
        .stream()
        .filter(x -> x.getLiveDead().equals(LiveDead.ACTIVE))
        .collect(Collectors.toList());
  }

  public Dependent findById(Integer id) {
    return dependentDao.getOne(id);
  }

  public Dependent persist(Dependent dependent) {
    if ( dependent.getId() == null ) {
      dependent.setCurrentStatus(CurrentStatus.ACT);
      dependent.setLiveDead(LiveDead.ACTIVE);
    }
    return dependentDao.save(dependent);
  }

  public boolean delete(Integer id) {
    Dependent dependent = dependentDao.getOne(id);
    dependent.setLiveDead(LiveDead.STOP);
    dependentDao.save(dependent);
    return false;
  }

  public List< Dependent > search(Dependent dependent) {
    return null;
  }


  public Dependent findByNic(String nic) {
    return dependentDao.findByNic(nic);
  }


/*    public List<Dependency> findByProvince(Province province) {
        return dependencyDao.findByProvince(province);
    }*/
}
