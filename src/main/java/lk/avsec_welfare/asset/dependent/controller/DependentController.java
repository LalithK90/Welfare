package lk.avsec_welfare.asset.dependent.controller;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import lk.avsec_welfare.asset.dependent.entity.Dependent;
import lk.avsec_welfare.asset.dependent.entity.DependentEmployee;
import lk.avsec_welfare.asset.dependent.entity.Enum.CurrentStatus;
import lk.avsec_welfare.asset.dependent.entity.Enum.InsideOrOut;
import lk.avsec_welfare.asset.dependent.entity.Enum.Relationship;
import lk.avsec_welfare.asset.dependent.service.DependentEmployeeService;
import lk.avsec_welfare.asset.dependent.service.DependentService;
import lk.avsec_welfare.asset.employee.entity.Employee;
import lk.avsec_welfare.asset.dependent.entity.Enum.BenefitedNot;
import lk.avsec_welfare.asset.employee.service.EmployeeService;
import lk.avsec_welfare.asset.userManagement.service.UserService;
import lk.avsec_welfare.util.service.DateTimeAgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping( "/dependent" )
public class DependentController {

  private final DependentService dependentService;
  private final DependentEmployeeService dependentEmployeeService;
  private final UserService userService;
  private final EmployeeService employeeService;
  private final DateTimeAgeService dateTimeAgeService;


  @Autowired
  public DependentController(DependentService dependentService, DependentEmployeeService dependentEmployeeService,
                             UserService userService, EmployeeService employeeService,
                             DateTimeAgeService dateTimeAgeService) {
    this.dependentService = dependentService;
    this.dependentEmployeeService = dependentEmployeeService;
    this.userService = userService;
    this.employeeService = employeeService;
    this.dateTimeAgeService = dateTimeAgeService;
  }

  private String commonThing(Model model, Boolean booleanValue, Dependent dependentObject) {
    model.addAttribute("relationship", Relationship.values());
    model.addAttribute("currentStatus", CurrentStatus.values());
    //  model.addAttribute("dependents", Province.values());
    model.addAttribute("addStatus", booleanValue);
    model.addAttribute("dependent", dependentObject);

    return "dependent/addDependent";
  }

  @GetMapping
  public String findAll(Model model) {
    model.addAttribute("dependents", dependentEmployeeService.findAll()
        .stream()
        .distinct()
        .collect(Collectors.toList()));
    model.addAttribute("contendHeader", "Dependent List");
    return "dependent/dependent";
  }

  @GetMapping( "/add/{id}" )
  public String form(@PathVariable Integer id, Model model) {
    Dependent newDependent = new Dependent();
    newDependent.setEmployee(employeeService.findById(id));
    model.addAttribute("contendHeader", "Add Dependent");

    return commonThing(model, false, newDependent);
  }

  @GetMapping( "/{id}" )
  public String findById(@PathVariable Integer id, Model model) {
    model.addAttribute("dependentDetail", dependentService.findById(id));
    model.addAttribute("contendHeader", "Dependent View Details");
    return "dependent/dependent-detail";
  }

  @GetMapping( "/edit/{id}" )
  public String edit(@PathVariable Integer id, Model model) {
    Dependent dependent = dependentService.findById(id);
    dependent.setEmployee(dependent.getDependentEmployees().get(0).getEmployeeOne());
    dependent.setRelationship(dependent.getDependentEmployees().get(0).getRelationship());
    model.addAttribute("contendHeader", "Dependent Edit Details");
    return commonThing(model, true, dependent);
  }

  @PostMapping( value = {"/save", "/update"} )
  public String persist(@Valid @ModelAttribute Dependent dependent, BindingResult bindingResult,
                        RedirectAttributes redirectAttributes, Model model) {
    Employee employee = employeeService.findById(dependent.getEmployee().getId());

    if ( (dependent.getRelationship().equals(Relationship.HUS) || dependent.getRelationship().equals(Relationship.WIF)) && employee.getEpf() != null ) {
      Employee employeeDb = employeeService.findByEpf(dependent.getEpfNumber());
      if ( employeeDb != null ) {
        ObjectError error = new ObjectError("dependent",
                                            "Why you try to be a dependent.");
        bindingResult.addError(error);
      }
    }


    if ( bindingResult.hasErrors() ) {
      return commonThing(model, false, dependent);
    }
//if there is on dependent.id that must be update
    if ( dependent.getId() != null ) {
      Dependent dependentDB = dependentService.persist(dependent);
      redirectAttributes.addFlashAttribute("dependentDetail",
                                           dependentEmployeeService.findByDependentAndEmployee(dependentDB, employee));
//      return "redirect:/dependent";
      return "redirect:/employee";
    }

    // when never dependent was new, have to
    DependentEmployee dependentEmployee = new DependentEmployee();
//if dependent has not id and dependent has epf number
    if ( dependent.getId() == null && dependent.getEpfNumber() != null ) {
      Employee companyEmployee = employeeService.findByEpf(dependent.getEpfNumber());
//company employee transfer to dependent
      Dependent dependentInternal = new Dependent();
      dependentInternal.setName(companyEmployee.getName());
      dependentInternal.setNic(companyEmployee.getNic());
      dependentInternal.setDob(companyEmployee.getDateOfBirth());
      dependentInternal.setCurrentStatus(CurrentStatus.ACT);
      dependentInternal.setRemark(dependent.getRemark());
      //name, nic, remark, dob, currentStatus;

      dependentEmployee.setDependent(dependentInternal);
      dependentEmployee.setEmployeeOne(employee);
      dependentEmployee.setInsideOrOut(InsideOrOut.IN);
      dependentEmployee.setRelationship(dependent.getRelationship());

      redirectAttributes.addFlashAttribute("dependentDetail",
                                           dependentEmployeeService.persist(dependentEmployee));
      return "redirect:/dependent";
    }
//if dependent has not id but has nic
    if ( dependent.getId() == null && dependent.getNic() != null ) {
      if ( dependentService.findByNic(dependent.getNic()) == null ) {
        //company employee transfer to dependent
        dependentEmployee.setDependent(makeDependent(dependent));
      } else {
        dependentEmployee.setDependent(dependentService.findByNic(dependent.getNic()));
      }

    } else {
      //if dependent has not id only
      dependentEmployee.setDependent(makeDependent(dependent));
    }
    dependentEmployee.setEmployeeOne(employee);
    dependentEmployee.setInsideOrOut(InsideOrOut.IN);
    dependentEmployee.setRelationship(dependent.getRelationship());
    redirectAttributes.addFlashAttribute("dependentDetail", dependentEmployeeService.persist(dependentEmployee));
    return "redirect:/dependent";
  }

  private Dependent makeDependent(Dependent dependent) {
    //name, nic, remark, dob, currentStatus;
    Dependent dependentNew = new Dependent();
    dependentNew.setName(dependent.getName());
    dependentNew.setNic(dependent.getNic());
    dependentNew.setDob(dependent.getDob());
    dependentNew.setCurrentStatus(CurrentStatus.ACT);
    dependentNew.setRemark(dependent.getRemark());
    return dependentNew;
  }

  @GetMapping( "/remove/{id}" )
  public String delete(@PathVariable Integer id) {
    dependentEmployeeService.delete(id);
    return "redirect:/dependent";
  }


  @GetMapping( "/employee/{id}" )
  @ResponseBody
  public MappingJacksonValue findByEmployee(@PathVariable( "id" ) Integer id) {

    List< DependentEmployee > dependentEmployee =
        dependentEmployeeService.findByEmployee(employeeService.findById(id)).stream().filter(x -> x.getBenefitedNot().equals(BenefitedNot.LIVE)).collect(Collectors.toList());

    List< Dependent > dependents = new ArrayList<>();

    dependentEmployee.forEach(x -> {
      if ( dateTimeAgeService.getAge(x.getDependent().getDob()) <= 18 ) {
        dependents.add(x.getDependent());
      }
    });

    MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(dependents);

    SimpleBeanPropertyFilter forBatch = SimpleBeanPropertyFilter
        .filterOutAllExcept("id", "name");

    FilterProvider filters = new SimpleFilterProvider()
        .addFilter("Dependent", forBatch);

    mappingJacksonValue.setFilters(filters);

    return mappingJacksonValue;
  }

}
/*
*
    Dependent saveDependant = null;
    //if there is registered dependent on system
    if ( !dependent.getNic().isEmpty() ) {
      System.out.println("existing dependent come to modified");
      Dependent dependentDb = dependentService.findByNic(dependent.getNic());
      if ( dependentDb == null ) {
        saveDependant = dependentService.persist(dependent);
      } else {
        saveDependant = dependentDb;
      }
     // redirectAttributes.addFlashAttribute("dependentDetail", saveDependant);
     // return "redirect:/dependent";
    }

    // create a new dependentEmployee and set value to it
    DependentEmployee dependentEmployee = new DependentEmployee();


    //relationship if wife husband -> employeeOne or employeeTwo
    if ( dependent.getRelationship().equals(Relationship.HUS) || dependent.getRelationship().equals(Relationship.WIF)
    *  && !dependent.getEpfNumber().isEmpty() ) {
      System.out.println("Dependent employee is AVSEC");
      Employee employeeDB = employeeService.findByEpf(dependent.getEpfNumber());
      if ( employeeDB.equals(employee) ) {
          redirectAttributes.addFlashAttribute("message", "Are you kidding me ? \n Do not try to add employee as
          * dependent on same employee...");

          return "redirect:/dependent";
      }
      //husband or wife
      dependentEmployee.setEmployeeTwo(employeeDB);
      //set relation ship
      dependentEmployee.setRelationship(dependent.getRelationship());
      //working employee
      dependentEmployee.setEmployeeOne(employee);

      //make new dependent using employee
      Dependent dependentAVSEC = new Dependent();
      //set employee status
      if ( employeeDB.getEmployeeStatus().equals(EmployeeStatus.RESIGNED) ) {
        dependentAVSEC.setCurrentStatus(CurrentStatus.OTR);
      } else {
        dependentAVSEC.setCurrentStatus(CurrentStatus.ACT);
      }
      dependentAVSEC.setDob(employeeDB.getDateOfBirth());
      dependentAVSEC.setName(employeeDB.getName());
      dependentAVSEC.setNic(employeeDB.getNic());

      //created dependent set to dependent employee
      dependentEmployee.setDependent(dependentAVSEC);
      dependentEmployee.setInsideOrOut(InsideOrOut.IN);

    } else {
      dependentEmployee.setRelationship(dependent.getRelationship());
      dependentEmployee.setEmployeeOne(employee);
      dependentEmployee.setDependent(saveDependant);
      dependentEmployee.setInsideOrOut(InsideOrOut.OUT);
    }
*
* */
