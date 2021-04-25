package lk.avsec_welfare.asset.employee_working_place.controller;


import lk.avsec_welfare.asset.employee.entity.Employee;
import lk.avsec_welfare.asset.employee.service.EmployeeService;
import lk.avsec_welfare.asset.employee_working_place.entity.EmployeeWorkingPlace;
import lk.avsec_welfare.asset.employee_working_place.entity.enums.WorkingPlaceChangeReason;
import lk.avsec_welfare.asset.employee_working_place.service.EmployeeWorkingPlaceService;
import lk.avsec_welfare.asset.working_place.service.WorkingPlaceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/employeeWorkingPlace")
public class EmployeeWorkingPlaceController {
  private final EmployeeService employeeService;
  private final WorkingPlaceService workingPlaceService;
  private final EmployeeWorkingPlaceService employeeWorkingPlaceService;

  public EmployeeWorkingPlaceController(EmployeeService employeeService, WorkingPlaceService workingPlaceService,
                                        EmployeeWorkingPlaceService employeeWorkingPlaceService) {
    this.employeeService = employeeService;
    this.workingPlaceService = workingPlaceService;
    this.employeeWorkingPlaceService = employeeWorkingPlaceService;
  }
  private String commonThing(Model model, Employee employee, EmployeeWorkingPlace employeeWorkingPlace, boolean addStatus){
    model.addAttribute("employeeDetail", employee);
    model.addAttribute("employeeWorkingPlace", employeeWorkingPlace);
    model.addAttribute("addStatus", addStatus);
    model.addAttribute("workingPlaceChangeReasons", WorkingPlaceChangeReason.values());
    model.addAttribute("workingPlaces", workingPlaceService.findAll());
    return "employeeWorkingPlace/addEmployeeWorkingPlace";
  }
  @GetMapping
  public String findAll(Model model) {
    model.addAttribute("employeeWorkingPlaces", employeeWorkingPlaceService.findAll());
    model.addAttribute("contendHeader", "Working Place");
    return "employeeWorkingPlace/employeeWorkingPlace";
  }

  @GetMapping( "/add/{id}" )
  public String form(@PathVariable Integer id, Model model) {
    model.addAttribute("contendHeader", "Add Working Place");
    return commonThing(model, employeeService.findById(id), new EmployeeWorkingPlace(), true);
  }

  @GetMapping( "/{id}" )
  public String findById(@PathVariable Integer id, Model model) {
    EmployeeWorkingPlace employeeWorkingPlace = employeeWorkingPlaceService.findById(id);
    model.addAttribute("employeeWorkingPlaceDetail", employeeWorkingPlace);
    model.addAttribute("employeeDetail", employeeWorkingPlace.getEmployee());
    model.addAttribute("instituteDetail", employeeWorkingPlace.getWorkingPlace());
    model.addAttribute("contendHeader", "Working Place Details");
    return "employeeWorkingPlace/employeeWorkingPlace-detail.html";
  }

  @GetMapping( "/edit/{id}" )
  public String edit(@PathVariable Integer id, Model model) {
    EmployeeWorkingPlace employeeWorkingPlace = employeeWorkingPlaceService.findById(id);
    model.addAttribute("contendHeader", "Update Working Place");
    return commonThing(model, employeeWorkingPlace.getEmployee(), employeeWorkingPlace,false);
  }

  @PostMapping( value = {"/save", "/update"} )
  public String persist(@Valid @ModelAttribute EmployeeWorkingPlace employeeWorkingPlace, BindingResult bindingResult,
                        Model model) {
    if ( bindingResult.hasErrors() ) {
      Employee employee = employeeService.findById(employeeWorkingPlace.getEmployee().getId());
      return commonThing(model, employee, employeeWorkingPlace,true);
    }

    Employee employee = employeeService.findById(employeeWorkingPlace.getEmployee().getId());
    if(employee.getWorkingPlace()==null){
      employee.setWorkingPlace(employeeWorkingPlace.getWorkingPlace());

    }else{
      employeeWorkingPlace.getWorkingPlace();
    }

employeeWorkingPlace.setEmployee(employee);
    employeeWorkingPlaceService.persist(employeeWorkingPlace);
    employeeService.persist(employee);
    return "redirect:/employee";
  }

  @GetMapping( "/delete/{id}" )
  public String delete(@PathVariable Integer id, Model model) {
    employeeWorkingPlaceService.delete(id);
    return "redirect:/qualification";
  }


}

