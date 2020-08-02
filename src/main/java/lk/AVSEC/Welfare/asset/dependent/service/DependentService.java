package lk.AVSEC.Welfare.asset.dependent.service;

import lk.AVSEC.Welfare.asset.dependent.dao.DependentDao;
import lk.AVSEC.Welfare.asset.dependent.entity.Dependent;
import lk.AVSEC.Welfare.asset.employee.entity.Employee;
import lk.AVSEC.Welfare.util.interfaces.AbstractService;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
// spring transactional annotation need to tell spring to this method work through the project
@CacheConfig(cacheNames = "dependency")
public class DependentService implements AbstractService<Dependent, Integer> {
    private final DependentDao dependentDao;

    public DependentService(DependentDao dependentDao) {
        this.dependentDao = dependentDao;
    }


    public List<Dependent> findAll() {
        return dependentDao.findAll();
    }

    public Dependent findById(Integer id) {
        return dependentDao.getOne(id);
    }

    public Dependent persist(Dependent dependent) {
        return dependentDao.save(dependent);
    }

    public boolean delete(Integer id) {
        dependentDao.deleteById(id);
        return false;
    }

    public List<Dependent> search(Dependent dependent) {
        return null;
    }

/*    public List<Dependent> findByEmployee(Employee employee) {
        List<Dependent> dependents = dependentDao.findByEmployee(employee);
        System.out.println("length  "+dependents.size());
        return dependents;
    }*/

    public Dependent findByNic(String nic) {
        return dependentDao.findByNic(nic);
    }


/*    public List<Dependency> findByProvince(Province province) {
        return dependencyDao.findByProvince(province);
    }*/
}
