package lk.avsec_welfare.asset.grievances.controller;


import lk.avsec_welfare.asset.employee.entity.Employee;
import lk.avsec_welfare.asset.employee.entity.enums.BoardOfDirectors;
import lk.avsec_welfare.asset.employee.entity.enums.WelfarePosition;
import lk.avsec_welfare.asset.employee.service.EmployeeFilesService;
import lk.avsec_welfare.asset.employee.service.EmployeeService;
import lk.avsec_welfare.asset.grievances.entity.enums.GrievancesStatus;
import lk.avsec_welfare.asset.grievances.entity.enums.Priority;
import lk.avsec_welfare.asset.grievances.entity.enums.SolutionType;
import lk.avsec_welfare.asset.grievances.entity.Grievance;
import lk.avsec_welfare.asset.grievances.entity.GrievanceStateChange;
import lk.avsec_welfare.asset.grievances.service.GrievanceStateChangeService;
import lk.avsec_welfare.asset.grievances.service.GrievancesService;
import lk.avsec_welfare.asset.userManagement.service.UserService;
import lk.avsec_welfare.util.interfaces.AbstractController;
import lk.avsec_welfare.util.service.DateTimeAgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping( "/grievances" )
public class GrievancesController implements AbstractController< Grievance, Integer > {
  private final GrievancesService grievancesService;
  private final UserService userService;
  private final DateTimeAgeService dateTimeAgeService;
  private final GrievanceStateChangeService grievanceStateChangeService;
  private final EmployeeService employeeService;
  private final EmployeeFilesService employeeFilesService;


  @Autowired
  public GrievancesController(GrievancesService grievancesService, UserService userService,
                              DateTimeAgeService dateTimeAgeService,
                              GrievanceStateChangeService grievanceStateChangeService,
                              EmployeeService employeeService, EmployeeFilesService employeeFilesService) {
    this.grievancesService = grievancesService;
    this.userService = userService;
    this.dateTimeAgeService = dateTimeAgeService;
    this.grievanceStateChangeService = grievanceStateChangeService;
    this.employeeService = employeeService;
    this.employeeFilesService = employeeFilesService;
  }

  private String commonThing(Model model, Boolean booleanValue, Grievance grievanceObject) {
    model.addAttribute("priorities", Priority.values());
    model.addAttribute("addStatus", booleanValue);
    model.addAttribute("grievances", grievanceObject);
    model.addAttribute("contendHeader", "Add Grievances");
    return "grievances/addGrievances";
  }

  @GetMapping
  public String findAll(Model model) {
    String userName = SecurityContextHolder.getContext().getAuthentication().getName();
    Employee employee = userService.findByUserName(userName).getEmployee();

    GrievancesStatus grievancesStatus;
    //log ing user -> switch
    //status check
    switch ( employee.getWelfarePosition().toString() ) {
      case "HOSS":
        grievancesStatus = GrievancesStatus.HOSS;
        break;
      case "PRE":
        grievancesStatus = GrievancesStatus.PRE;
        break;
      default:
        grievancesStatus = GrievancesStatus.SCTY;
    }
    //date range (one month)
    LocalDate to = dateTimeAgeService.getCurrentDate();
    LocalDate form = to.minusDays(30);
//solution type, date range, grievance Status
    System.out.println("grivence11111   " + grievancesStatus.getGrievancesStatus());

    if ( employee.getWelfarePosition().equals(WelfarePosition.HOSS) ||

        employee.getWelfarePosition().equals(WelfarePosition.PRE) ||
        employee.getWelfarePosition().equals(WelfarePosition.SCTY) ) {

      List< Grievance > toPending =
          grievancesService.findBySolutionTypeAndGrievancesStatusAndCreatedAtBetween(SolutionType.PR, grievancesStatus,
                                                                                     dateTimeAgeService.dateTimeToLocalDateStartInDay(form), dateTimeAgeService.dateTimeToLocalDateEndInDay(to));
      List< Grievance > toClose =
          grievancesService.findBySolutionTypeAndGrievancesStatusAndCreatedAtBetween(SolutionType.CL, grievancesStatus,
                                                                                     dateTimeAgeService.dateTimeToLocalDateStartInDay(form), dateTimeAgeService.dateTimeToLocalDateEndInDay(to));

      if ( toPending.size() != 0 ) {
        model.addAttribute("toPending", toPending);

      }
      if ( toClose.size() != 0 ) {
        model.addAttribute("toClose", toClose);

      }
    }
    List< Grievance > personalToPending =
        grievancesService.findBySolutionTypeAndCreatedByAndCreatedAtBetween(SolutionType.PR, userName,
                                                                            dateTimeAgeService.dateTimeToLocalDateStartInDay(form), dateTimeAgeService.dateTimeToLocalDateEndInDay(to));
    List< Grievance > personalToClose =
        grievancesService.findBySolutionTypeAndCreatedByAndCreatedAtBetween(SolutionType.CL, userName,
                                                                            dateTimeAgeService.dateTimeToLocalDateStartInDay(form), dateTimeAgeService.dateTimeToLocalDateEndInDay(to));

    if ( personalToPending.size() != 0 ) {
      model.addAttribute("personalToPending", personalToPending);
    }
    if ( personalToClose.size() != 0 ) {
      model.addAttribute("personalToClose", personalToClose);
    }

    model.addAttribute("form", form);
    model.addAttribute("to", to);
    model.addAttribute("contendHeader", "Grievances");
    return "grievances/grievances";
  }

  @GetMapping( "/add" )
  public String form(Model model) {
    model.addAttribute("contendHeader", "Add Grievances");
    return commonThing(model, false, new Grievance());

  }

  @GetMapping( "/{id}" )
  public String findById(@PathVariable Integer id, Model model) {
    Grievance grievance = grievancesService.findById(id);
    Employee employee =
        employeeService.findById(userService.findByUserName(grievance.getCreatedBy()).getEmployee().getId());
    model.addAttribute("grievancesDetail", grievance);
    model.addAttribute("employeeDetail", employee);
    model.addAttribute("addStatus", false);
    model.addAttribute("contendHeader", "Grievances View Details");
    model.addAttribute("files", employeeFilesService.employeeFileDownloadLinks(employee));
    return "grievances/grievances-detail";
  }

  @GetMapping( "/edit/{id}" )
  public String edit(@PathVariable Integer id, Model model) {
    model.addAttribute("grievancesStatuses", GrievancesStatus.values());
    return commonThing(model, true, grievancesService.findById(id));
  }

  @PostMapping( value = {"/save", "/update"} )
  public String persist(@Valid @ModelAttribute Grievance grievance, BindingResult bindingResult,
                        RedirectAttributes redirectAttributes, Model model) {
    if ( bindingResult.hasErrors() ) {
      return commonThing(model, false, grievance);
    }
    if ( grievance.getRemark() != null && grievance.getId() != null ) {

      GrievanceStateChange grievanceStateChange = new GrievanceStateChange();
      grievanceStateChange.setRemark(grievance.getRemark());
      grievanceStateChange.setGrievance(grievance);
      grievanceStateChange.setCommentedBy(userService.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName()).getEmployee().getCallingName());
      grievanceStateChangeService.persist(grievanceStateChange);
    }
    System.out.println("greavances GrievancesStatus"+grievance.getGrievancesStatus());
    System.out.println("greavances Priority"+grievance.getPriority());
    System.out.println("greavances Solution Type"+grievance.getSolutionType());
    redirectAttributes.addFlashAttribute("grievancesDetail", grievancesService.persist(grievance));
    return "redirect:/grievances";
  }

  @GetMapping( "/delete/{id}" )
  public String delete(@PathVariable Integer id, Model model) {
    grievancesService.delete(id);
    return "redirect:/grievances";
  }

  @GetMapping( "/action/{id}" )
  public String action(@PathVariable Integer id, Model model) {
    model.addAttribute("solutionTypes", SolutionType.values());
    model.addAttribute("grievancesStatuses", GrievancesStatus.values());
    model.addAttribute("actionDetail", true);
    return commonThing(model, true, grievancesService.findById(id));
  }

}
