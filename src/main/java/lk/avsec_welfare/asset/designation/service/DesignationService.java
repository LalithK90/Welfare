package lk.avsec_welfare.asset.designation.service;

import lk.avsec_welfare.asset.common_asset.model.enums.LiveDead;
import lk.avsec_welfare.asset.designation.dao.DesignationDao;
import lk.avsec_welfare.asset.designation.entity.Designation;
import lk.avsec_welfare.util.interfaces.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service

@CacheConfig( cacheNames = "designation" )
public class DesignationService implements AbstractService< Designation, Integer > {

  private final DesignationDao designationDao;

  @Autowired
  public DesignationService(DesignationDao designationDao) {
    this.designationDao = designationDao;
  }

  public List< Designation > findAll() {
    return designationDao.findAll().stream().filter(x -> x.getLiveDead().equals(LiveDead.ACTIVE)).collect(Collectors.toList());
  }

  public Designation findById(Integer id) {
    return designationDao.getOne(id);
  }

  public Designation persist(Designation designation) {
    if ( designation.getId() == null ) {
      designation.setLiveDead(LiveDead.ACTIVE);
    }
    return designationDao.save(designation);
  }

  public boolean delete(Integer id) {
    Designation designation = designationDao.getOne(id);
    designation.setLiveDead(LiveDead.STOP);
    designationDao.save(designation);
    return false;
  }

  public List< Designation > search(Designation designation) {
    ExampleMatcher matcher = ExampleMatcher
        .matching()
        .withIgnoreCase()
        .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
    Example< Designation > designationExample = Example.of(designation, matcher);
    return designationDao.findAll(designationExample);
  }

}
