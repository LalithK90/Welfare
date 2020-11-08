package lk.AVSEC.Welfare.asset.dependent.controller;

import lk.AVSEC.Welfare.asset.dependent.entity.Dependent;
import lk.AVSEC.Welfare.asset.dependent.entity.DependentEmployee;
import lk.AVSEC.Welfare.asset.dependent.entity.Enum.CurrentStatus;
import lk.AVSEC.Welfare.asset.dependent.entity.Enum.InsideOrOut;
import lk.AVSEC.Welfare.asset.dependent.entity.Enum.Relationship;
import lk.AVSEC.Welfare.asset.dependent.service.DependentEmployeeService;
import lk.AVSEC.Welfare.asset.dependent.service.DependentService;
import lk.AVSEC.Welfare.asset.employee.entity.Employee;
import lk.AVSEC.Welfare.asset.employee.entity.Enum.EmployeeStatus;
import lk.AVSEC.Welfare.asset.employee.service.EmployeeService;
import lk.AVSEC.Welfare.asset.userManagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.stream.Collectors;

@Controller
@RequestMapping( "/dependent" )
public class DependentController {

  private final DependentService dependentService;
  private final DependentEmployeeService dependentEmployeeService;
  private final UserService userService;
  private final EmployeeService employeeService;


  @Autowired
  public DependentController(DependentService dependentService, DependentEmployeeService dependentEmployeeService,
                             UserService userService, EmployeeService employeeService) {
    this.dependentService = dependentService;
    this.dependentEmployeeService = dependentEmployeeService;
    this.userService = userService;
    this.employeeService = employeeService;
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
    model.addAttribute("contendHeader", "Dependent Edit Details");
    return commonThing(model, true, dependentService.findById(id));
 }

  @PostMapping( value = {"/save", "/update"} )
  public String persist(@Valid @ModelAttribute Dependent dependent, BindingResult bindingResult,
                        RedirectAttributes redirectAttributes, Model model) {
    Employee employee = employeeService.findById(dependent.getEmployee().getId());

    if ( bindingResult.hasErrors() ) {
      return commonThing(model, false, dependent);
    }

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
//      redirectAttributes.addFlashAttribute("dependentDetail", saveDependant);
//      return "redirect:/dependent";
    }

    // create a new dependentEmployee and set value to it
    DependentEmployee dependentEmployee = new DependentEmployee();


    //relationship if wife husband -> employeeOne or employeeTwo
    if ( dependent.getRelationship().equals(Relationship.HUS) || dependent.getRelationship().equals(Relationship.WIF) && !dependent.getEpfNumber().isEmpty() ) {
      System.out.println("Dependent employee is AVSEC");
      Employee employeeDB = employeeService.findByEpf(dependent.getEpfNumber());
      if ( employeeDB.equals(employee) ) {
          redirectAttributes.addFlashAttribute("message", "Are you kidding me ? \n Do not try to add employee as depender on same employee...");

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


    redirectAttributes.addFlashAttribute("dependentDetail", dependentEmployeeService.persist(dependentEmployee));
    return "redirect:/dependent";
  }

  @GetMapping( "/delete/{id}" )
  public String delete(@PathVariable Integer id) {
    dependentService.delete(id);
    return "redirect:/dependent";
  }


}
