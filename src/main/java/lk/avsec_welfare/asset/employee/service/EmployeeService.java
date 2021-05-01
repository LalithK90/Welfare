package lk.avsec_welfare.asset.employee.service;

import lk.avsec_welfare.asset.common_asset.model.enums.LiveDead;
import lk.avsec_welfare.asset.employee.dao.EmployeeDao;
import lk.avsec_welfare.asset.employee.entity.Employee;
import lk.avsec_welfare.asset.employee.entity.enums.BoardOfDirectors;
import lk.avsec_welfare.util.interfaces.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
// spring transactional annotation need to tell spring to this method work through the project

public class EmployeeService implements AbstractService< Employee, Integer > {

  private final EmployeeDao employeeDao;

  @Autowired
  public EmployeeService(EmployeeDao employeeDao) {
    this.employeeDao = employeeDao;
  }


  public List< Employee > findAll() {
    return employeeDao.findAll().stream().filter(x -> x.getLiveDead().equals(LiveDead.ACTIVE)).collect(Collectors.toList());
  }


  public Employee findById(Integer id) {
    return employeeDao.getOne(id);
  }


  public Employee persist(Employee employee) {
    if ( employee.getBoardOfDirectors() == null ) {
      employee.setBoardOfDirectors(BoardOfDirectors.MBR);
      employee.setLiveDead(LiveDead.ACTIVE);
    }
    return employeeDao.save(employee);
  }


  public boolean delete(Integer id) {
    Employee employee = employeeDao.getOne(id);
    employee.setLiveDead(LiveDead.STOP);
    employeeDao.save(employee);
    return false;
  }


  public List< Employee > search(Employee employee) {
    ExampleMatcher matcher = ExampleMatcher
        .matching()
        .withIgnoreCase()
        .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
    Example< Employee > employeeExample = Example.of(employee, matcher);
    return employeeDao.findAll(employeeExample);
  }

  public boolean isEmployeePresent(Employee employee) {
    return employeeDao.equals(employee);
  }


  public Employee lastEmployee() {
    return employeeDao.findFirstByOrderByIdDesc();
  }


  public Employee findByNic(String nic) {
    return employeeDao.findByNic(nic);
  }

  public Employee findByEpf(String epf) {
    return employeeDao.findByEpf(epf);
  }

  public List< Employee > findByCreatedAtBetween(LocalDateTime form, LocalDateTime to) {
    return employeeDao.findByCreatedAtBetween(form, to);
  }



}
