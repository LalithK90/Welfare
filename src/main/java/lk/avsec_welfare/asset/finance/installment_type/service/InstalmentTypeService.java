package lk.avsec_welfare.asset.finance.installment_type.service;

import lk.avsec_welfare.asset.finance.installment_type.dao.InstalmentTypeDao;
import lk.avsec_welfare.asset.finance.installment_type.entity.InstalmentType;
import lk.avsec_welfare.util.interfaces.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstalmentTypeService implements AbstractService< InstalmentType, Integer > {

  private final InstalmentTypeDao instalmentTypeDao;

  @Autowired
  public InstalmentTypeService(InstalmentTypeDao instalmentTypeDao) {
    this.instalmentTypeDao = instalmentTypeDao;
  }

  public List< InstalmentType > findAll() {
    return instalmentTypeDao.findAll();
  }

  public InstalmentType findById(Integer id) {
    return instalmentTypeDao.getOne(id);
  }

  public InstalmentType persist(InstalmentType instalmentType) {
    return instalmentTypeDao.save(instalmentType);
  }

  public boolean delete(Integer id) {
    instalmentTypeDao.deleteById(id);
    return true;
  }

  public List< InstalmentType > search(InstalmentType instalmentType) {
    ExampleMatcher matcher = ExampleMatcher
        .matching()
        .withIgnoreCase()
        .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
    Example< InstalmentType > debitExample = Example.of(instalmentType, matcher);
    return instalmentTypeDao.findAll(debitExample);
  }

  public List< InstalmentType > findByYear(String paidYear) {
    return instalmentTypeDao.findByYear(paidYear);
  }
}
