package lk.AVSEC.Welfare.asset.dependency.service;

import lk.AVSEC.Welfare.asset.dependency.dao.DependencyDao;
import lk.AVSEC.Welfare.asset.dependency.entity.Dependency;
import lk.AVSEC.Welfare.util.interfaces.AbstractService;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
// spring transactional annotation need to tell spring to this method work through the project
@CacheConfig(cacheNames = "dependency")
public class DependencyService implements AbstractService<Dependency, Integer> {
    private final DependencyDao dependencyDao;

    public DependencyService(DependencyDao dependencyDao) {
        this.dependencyDao = dependencyDao;
    }


    public List<Dependency> findAll() {
        return dependencyDao.findAll();
    }

    public Dependency findById(Integer id) {
        return dependencyDao.getOne(id);
    }

    public Dependency persist(Dependency dependency) {
        return dependencyDao.save(dependency);
    }

    public boolean delete(Integer id) {
        dependencyDao.deleteById(id);
        return false;
    }

    public List<Dependency> search(Dependency dependency) {
        return null;
    }


/*    public List<Dependency> findByProvince(Province province) {
        return dependencyDao.findByProvince(province);
    }*/
}
