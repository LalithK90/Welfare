package lk.avsec_welfare.asset.designation.service;

import lk.avsec_welfare.asset.designation.dao.DesignationDao;
import lk.avsec_welfare.asset.designation.entity.Designation;
import lk.avsec_welfare.util.interfaces.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

@CacheConfig(cacheNames = "designation")
public class DesignationService implements AbstractService<Designation, Integer> {

    private final DesignationDao designationDao;

    @Autowired
    public DesignationService(DesignationDao designationDao) {

        this.designationDao = designationDao;
    }

    public List<Designation> findAll() {
        return designationDao.findAll();
    }

    public Designation findById(Integer id) {
        return designationDao.getOne(id);
    }

    public Designation persist(Designation designation) {
        return designationDao.save(designation);
    }

    public boolean delete(Integer id) {
        designationDao.deleteById(id);
        return false;
    }

    public List<Designation> search(Designation designation) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Designation> designationExample = Example.of(designation, matcher);
        return designationDao.findAll(designationExample);
    }

}
