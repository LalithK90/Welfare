package lk.AVSEC.Welfare.asset.employee_working_place.service;


import lk.AVSEC.Welfare.asset.employee_working_place.dao.EmployeeWorkingPlaceDao;
import lk.AVSEC.Welfare.asset.employee_working_place.entity.EmployeeWorkingPlace;
import lk.AVSEC.Welfare.util.interfaces.AbstractService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeWorkingPlaceService implements AbstractService<EmployeeWorkingPlace, Integer > {
  private final EmployeeWorkingPlaceDao employeeInstituteDao;

  public EmployeeWorkingPlaceService(EmployeeWorkingPlaceDao employeeInstituteDao) {
    this.employeeInstituteDao = employeeInstituteDao;
  }

  public List< EmployeeWorkingPlace > findAll() {
    return employeeInstituteDao.findAll();
  }

  public EmployeeWorkingPlace findById(Integer id) {
    return employeeInstituteDao.getOne(id);
  }

  public EmployeeWorkingPlace persist(EmployeeWorkingPlace employeeInstitute) {
    return employeeInstituteDao.save(employeeInstitute);
  }

  public boolean delete(Integer id) {
    employeeInstituteDao.deleteById(id);
    return true;
  }

  public List< EmployeeWorkingPlace > search(EmployeeWorkingPlace employeeInstitute) {
    ExampleMatcher matcher = ExampleMatcher
        .matching()
        .withIgnoreCase()
        .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
    Example< EmployeeWorkingPlace > employeeInstituteExample = Example.of(employeeInstitute, matcher);
    return employeeInstituteDao.findAll(employeeInstituteExample);
  }
}
