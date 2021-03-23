package lk.avsec_welfare.asset.dependent.service;

import lk.avsec_welfare.asset.common_asset.model.enums.LiveDead;
import lk.avsec_welfare.asset.dependent.dao.DependentEmployeeDao;
import lk.avsec_welfare.asset.dependent.entity.Dependent;
import lk.avsec_welfare.asset.dependent.entity.DependentEmployee;
import lk.avsec_welfare.asset.employee.entity.Employee;
import lk.avsec_welfare.util.interfaces.AbstractService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
// spring transactional annotation need to tell spring to this method work through the project
@CacheConfig( cacheNames = "dependentEmployee" )
public class DependentEmployeeService implements AbstractService< DependentEmployee, Integer > {
  private final DependentEmployeeDao dependentEmployeeDao;

  public DependentEmployeeService(DependentEmployeeDao dependentEmployeeDao) {
    this.dependentEmployeeDao = dependentEmployeeDao;
  }


  public List< DependentEmployee > findAll() {
    return dependentEmployeeDao.findAll().stream().filter(x -> x.getLiveDead().equals(LiveDead.ACTIVE)).collect(Collectors.toList());
  }

  public DependentEmployee findById(Integer id) {
    return dependentEmployeeDao.getOne(id);
  }

  public DependentEmployee persist(DependentEmployee dependent) {
    if ( dependent.getId() == null ) {
      dependent.setLiveDead(LiveDead.ACTIVE);
    }
    return dependentEmployeeDao.save(dependent);
  }

  public boolean delete(Integer id) {
    DependentEmployee dependentEmployee = dependentEmployeeDao.getOne(id);
    dependentEmployee.setLiveDead(LiveDead.STOP);
    dependentEmployeeDao.save(dependentEmployee);
    return false;
  }

  public List< DependentEmployee > search(DependentEmployee dependent) {
    return null;
  }

  public List< DependentEmployee > findByEmployee(Employee employee) {
    List< DependentEmployee > dependents = dependentEmployeeDao.findByEmployeeOne(employee);
    System.out.println("length  " + dependents.size());
    return dependents;
  }

  public Dependent findByDependent(Dependent dependent) {
    return dependentEmployeeDao.findByDependent(dependent);
  }

  public DependentEmployee findByDependentAndEmployee(Dependent dependent, Employee employee) {
    return dependentEmployeeDao.findByDependentAndEmployeeOne(dependent, employee);

  }


/*    public List<Dependency> findByProvince(Province province) {
        return dependencyDao.findByProvince(province);
    }*/
}
