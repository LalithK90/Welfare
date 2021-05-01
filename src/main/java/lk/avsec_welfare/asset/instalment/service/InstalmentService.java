package lk.avsec_welfare.asset.instalment.service;

import lk.avsec_welfare.asset.common_asset.model.enums.LiveDead;
import lk.avsec_welfare.asset.employee.entity.Employee;
import lk.avsec_welfare.asset.instalment.dao.InstalmentDao;
import lk.avsec_welfare.asset.instalment.entity.Instalment;
import lk.avsec_welfare.asset.instalment.entity.enums.InstalmentStatus;
import lk.avsec_welfare.util.interfaces.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InstalmentService implements AbstractService< Instalment, Integer > {

  private final InstalmentDao instalmentDao;

  @Autowired
  public InstalmentService(InstalmentDao instalmentDao) {
    this.instalmentDao = instalmentDao;
  }

  public List< Instalment > findAll() {
    return instalmentDao.findAll().stream().filter(x -> x.getLiveDead().equals(LiveDead.ACTIVE)).collect(Collectors.toList());
  }

  public Instalment findById(Integer id) {
    return instalmentDao.getOne(id);
  }

  public Instalment persist(Instalment instalment) {
    if ( instalment.getId() == null ) {
      instalment.setLiveDead(LiveDead.ACTIVE);
    }
    return instalmentDao.save(instalment);
  }

  public boolean delete(Integer id) {
    Instalment instalment = instalmentDao.getOne(id);
    instalment.setLiveDead(LiveDead.STOP);
    instalmentDao.save(instalment);
    return true;
  }

  public List< Instalment > search(Instalment instalment) {
    ExampleMatcher matcher = ExampleMatcher
        .matching()
        .withIgnoreCase()
        .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
    Example< Instalment > debitExample = Example.of(instalment, matcher);
    return instalmentDao.findAll(debitExample);
  }

  public List< Instalment > findByEmployee(Employee employee) {
    return instalmentDao.findByEmployee(employee);
  }

  public List< Instalment > findByInstalmentStatus(InstalmentStatus instalmentStatus) {
    return instalmentDao.findByInstalmentStatus(instalmentStatus);
  }

  public List< Instalment > findByCreatedAtIsBetweenAndCreatedBy(LocalDateTime startDateTime, LocalDateTime endDateTime, String username) {
  return instalmentDao.findByCreatedAtIsBetweenAndCreatedBy(startDateTime,endDateTime,username);
  }
}
