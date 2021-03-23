package lk.avsec_welfare.asset.employee_working_place.service;


import lk.avsec_welfare.asset.common_asset.model.enums.LiveDead;
import lk.avsec_welfare.asset.employee.entity.Employee;
import lk.avsec_welfare.asset.employee_working_place.dao.EmployeeWorkingPlaceDao;
import lk.avsec_welfare.asset.employee_working_place.entity.EmployeeWorkingPlace;
import lk.avsec_welfare.util.interfaces.AbstractService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeWorkingPlaceService implements AbstractService<EmployeeWorkingPlace, Integer > {
  private final EmployeeWorkingPlaceDao employeeWorkingPlaceDao;

  public EmployeeWorkingPlaceService(EmployeeWorkingPlaceDao employeeWorkingPlaceDao) {
    this.employeeWorkingPlaceDao = employeeWorkingPlaceDao;
  }

  public List< EmployeeWorkingPlace > findAll() {
    return employeeWorkingPlaceDao.findAll().stream().filter(x->x.getLiveDead().equals(LiveDead.ACTIVE)).collect(Collectors.toList());
  }

  public EmployeeWorkingPlace findById(Integer id) {
    return employeeWorkingPlaceDao.getOne(id);
  }

  public EmployeeWorkingPlace persist(EmployeeWorkingPlace employeeInstitute) {
    if ( employeeInstitute.getId() ==null ){
      employeeInstitute.setLiveDead(LiveDead.ACTIVE);
    }
    return employeeWorkingPlaceDao.save(employeeInstitute);
  }

  public boolean delete(Integer id) {
    EmployeeWorkingPlace employeeWorkingPlace = employeeWorkingPlaceDao.getOne(id);
    employeeWorkingPlace.setLiveDead(LiveDead.STOP);
    employeeWorkingPlaceDao.save(employeeWorkingPlace);
    return true;
  }

  public List< EmployeeWorkingPlace > search(EmployeeWorkingPlace employeeInstitute) {
    ExampleMatcher matcher = ExampleMatcher
        .matching()
        .withIgnoreCase()
        .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
    Example< EmployeeWorkingPlace > employeeInstituteExample = Example.of(employeeInstitute, matcher);
    return employeeWorkingPlaceDao.findAll(employeeInstituteExample);
  }
}
