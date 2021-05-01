package lk.avsec_welfare.asset.employee.controller;

import lk.avsec_welfare.asset.briefing.service.BriefingService;
import lk.avsec_welfare.asset.censure.service.CensureService;
import lk.avsec_welfare.asset.common_asset.model.enums.*;
import lk.avsec_welfare.asset.dependent.entity.Enum.CurrentStatus;
import lk.avsec_welfare.asset.dependent.entity.Enum.Relationship;
import lk.avsec_welfare.asset.dependent.service.DependentEmployeeService;
import lk.avsec_welfare.asset.dependent.service.DependentService;
import lk.avsec_welfare.asset.designation.service.DesignationService;
import lk.avsec_welfare.asset.employee.entity.Employee;
import lk.avsec_welfare.asset.employee.entity.EmployeeFiles;
import lk.avsec_welfare.asset.employee.entity.enums.EmployeeStatus;
import lk.avsec_welfare.asset.employee.entity.enums.Nationality;
import lk.avsec_welfare.asset.employee.entity.enums.UniformType;
import lk.avsec_welfare.asset.employee.entity.enums.WelfarePosition;
import lk.avsec_welfare.asset.employee.service.EmployeeFilesService;
import lk.avsec_welfare.asset.employee.service.EmployeeService;
import lk.avsec_welfare.asset.employee_working_place.service.EmployeeWorkingPlaceService;
import lk.avsec_welfare.asset.userManagement.entity.User;
import lk.avsec_welfare.asset.userManagement.service.UserService;
import lk.avsec_welfare.asset.working_place.service.WorkingPlaceService;
import lk.avsec_welfare.util.service.DateTimeAgeService;
import lk.avsec_welfare.util.service.MakeAutoGenerateNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.*;


@Controller
@RequestMapping( "/employee" )
public class EmployeeController {
  private final EmployeeService employeeService;
  private final CensureService censureService;
  private final EmployeeFilesService employeeFilesService;
  private final DateTimeAgeService dateTimeAgeService;
  private final WorkingPlaceService workingPlaceService;
  private final EmployeeWorkingPlaceService employeeWorkingPlaceService;
  private final DependentEmployeeService dependentEmployeeService;
  private final UserService userService;
  private final DesignationService designationService;
  private final DependentService dependentService;
  private final MakeAutoGenerateNumberService makeAutoGenerateNumberService;

  @Autowired
  public EmployeeController(EmployeeService employeeService, EmployeeFilesService employeeFilesService,
                            DateTimeAgeService dateTimeAgeService, WorkingPlaceService workingPlaceService,
                            EmployeeWorkingPlaceService employeeWorkingPlaceService,
                            DependentEmployeeService dependentEmployeeService, UserService userService,
                            DesignationService designationService, CensureService censureService,
                            DependentService dependentService,
                            BriefingService briefingService,
                            MakeAutoGenerateNumberService makeAutoGenerateNumberService) {
    this.employeeService = employeeService;
    this.employeeFilesService = employeeFilesService;

    this.dateTimeAgeService = dateTimeAgeService;
    this.workingPlaceService = workingPlaceService;
    this.employeeWorkingPlaceService = employeeWorkingPlaceService;
    this.dependentEmployeeService = dependentEmployeeService;
    this.userService = userService;
    this.censureService = censureService;
    this.designationService = designationService;
    this.dependentService = dependentService;
    this.makeAutoGenerateNumberService = makeAutoGenerateNumberService;
  }
//----> Employee details management - start <----//

  // Common things for an employee add and update
  private String commonThings(Model model) {
    model.addAttribute("title", Title.values());
    model.addAttribute("gender", Gender.values());
    model.addAttribute("civilStatus", CivilStatus.values());
    model.addAttribute("employeeStatus", EmployeeStatus.values());
    model.addAttribute("designation", designationService.findAll());
    model.addAttribute("employees", employeeService.findAll());
    model.addAttribute("bloodGroup", BloodGroup.values());
    model.addAttribute("nationality", Nationality.values());
    model.addAttribute("religion", Religion.values());
    model.addAttribute("uniformType", UniformType.values());
    model.addAttribute("relationship", Relationship.values());
    model.addAttribute("currentStatus", CurrentStatus.values());
    model.addAttribute("workingPlaces", workingPlaceService.findAll());
    model.addAttribute("welfarePositions", WelfarePosition.values());
    return "employee/addEmployee";
  }

  //When scr called file will send to
  @GetMapping( "/file/{filename}" )
  public ResponseEntity< byte[] > downloadFile(@PathVariable( "filename" ) String filename) {
    EmployeeFiles file = employeeFilesService.findByNewID(filename);
    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
        .body(file.getPic());
  }

  //Send all employee data
  @RequestMapping
  public String employeePage(Model model) {
    List< Employee > employees = new ArrayList<>();
    for ( Employee employee : employeeService.findAll() ) {
      employee.setFileInfo(employeeFilesService.employeeFileDownloadLinks(employee));
      employees.add(employee);
    }
    /*  Employee employee = employeeService.findById(id);*/
    model.addAttribute("employees", employees);
    model.addAttribute("contendHeader", "Employee List");
//        /* model.addAttribute("files", employeeFilesService.employeeFileDownloadLinks(employee));*/
    return "employee/employee";
  }

  //Send on employee details
  @GetMapping( value = "/{id}" )
  public String employeeView(@PathVariable( "id" ) Integer id, Model model) {
    Employee employee = employeeService.findById(id);
    model.addAttribute("files", employeeFilesService.employeeFileDownloadLinks(employee));
    model.addAttribute("employeeDetail", employee);
    model.addAttribute("employeeWorkingPlaces", employeeWorkingPlaceService.findByEmployee(employee));
    model.addAttribute("addStatus", false);
    model.addAttribute("dependentEmployees", dependentEmployeeService.findByEmployee(employee));
    model.addAttribute("contendHeader", "Employee View Details");
    return "employee/employee-detail";
  }

  //Send employee data edit
  @GetMapping( value = "/edit/{id}" )
  public String editEmployeeForm(@PathVariable( "id" ) Integer id, Model model) {
    Employee employee = employeeService.findById(id);
    employee.setCensures(censureService.findByEmployee(employee));
    model.addAttribute("employee", employee);
    model.addAttribute("newEmployee", employee.getEpf());

    model.addAttribute("addStatus", false);
    model.addAttribute("contendHeader", "Employee Edit Details");
    model.addAttribute("file", employeeFilesService.employeeFileDownloadLinks(employee));
    return commonThings(model);
  }

  //Send an employee add form
  @GetMapping( value = {"/add"} )
  public String employeeAddForm(Model model) {
    model.addAttribute("addStatus", true);
    model.addAttribute("employee", new Employee());
    model.addAttribute("contendHeader", "Employee Add");
    return commonThings(model);
  }

  @GetMapping( value = "/findAll" )
  public String findAllForm(Model model) {
    model.addAttribute("employee", new Employee());
    return "employee/findEmployee";
  }

  @PostMapping( value = "/findAll" )
  public String findAll(@ModelAttribute( "employee" ) Employee employee, Model model) {

    List< Employee > employees = employeeService.search(employee);

    if ( employees == null ) {
      model.addAttribute("employeeNotFound", "There is not employee in the system according to the provided details" +
          " or that employee already be a user in the system" +
          " \n Could you please search again !!");
    } else {
      model.addAttribute("employees", employees);
    }

    model.addAttribute("employee", new Employee());
    return "employee/findEmployee";
  }


  //Employee add and update
  @PostMapping( value = {"/save", "/update"} )
  public String addEmployee(@Valid @ModelAttribute Employee employee, BindingResult result, Model model) {
    if ( result.hasErrors() ) {
      model.addAttribute("addStatus", true);
      model.addAttribute("employee", employee);
      return commonThings(model);
    }


    employee.setMobileOne(makeAutoGenerateNumberService.phoneNumberLengthValidator(employee.getMobileOne()));
    employee.setMobileTwo(makeAutoGenerateNumberService.phoneNumberLengthValidator(employee.getMobileTwo()));
    employee.setLand(makeAutoGenerateNumberService.phoneNumberLengthValidator(employee.getLand()));

    if ( employee.getId() == null ) {
      employee.setWorkingPlace(workingPlaceService.findById(1));
      Employee lastEmployee = employeeService.lastEmployee();
      if ( lastEmployee.getDepartmentIdNumber() == null ) {
        employee.setDepartmentIdNumber("AVE" + makeAutoGenerateNumberService.numberAutoGen(null).toString());
      } else {
        employee.setDepartmentIdNumber("AVE" + makeAutoGenerateNumberService.numberAutoGen(lastEmployee.getDepartmentIdNumber().substring(3)).toString());
      }
    }
    Employee employeeSaved = null;

    try {
      //after save employee files and save employee
      employeeSaved = employeeService.persist(employee);
    } catch ( Exception e ) {
      ObjectError error = new ObjectError("subject",
                                          "Please fix following errors which you entered .\n System message -->" + e.getCause().getCause().getMessage());
      result.addError(error);
      model.addAttribute("addStatus", true);
      model.addAttribute("employee", employee);
      return commonThings(model);
    }


    //if employee state is not working he or she cannot access to the system
    if ( !employee.getEmployeeStatus().equals(EmployeeStatus.WORKING) ) {
      User user = userService.findUserByEmployee(employeeService.findByNic(employee.getNic()));
      //if employee not a user
      if ( user != null ) {
        user.setEnabled(false);
        userService.persist(user);
      }
    }
    try {
      //save employee images file
      if ( employee.getFile().getOriginalFilename() != null && !Objects.requireNonNull(employee.getFile().getContentType()).equals("application/octet-stream") ) {
        EmployeeFiles employeeFiles = employeeFilesService.findByEmployee(employeeSaved);
        if ( employeeFiles != null ) {
          // update new contents
          employeeFiles.setPic(employee.getFile().getBytes());
          // Save all to database
        } else {
          employeeFiles = new EmployeeFiles(employee.getFile().getOriginalFilename(),
                                            employee.getFile().getContentType(),
                                            employee.getFile().getBytes(),
                                            employee.getNic().concat("-" + LocalDateTime.now()),
                                            UUID.randomUUID().toString().concat("employee"));
          employeeFiles.setEmployee(employee);
        }
        employeeFilesService.persist(employeeFiles);
      }
      return "redirect:/employee";

    } catch ( Exception e ) {
      ObjectError error = new ObjectError("employee",
                                          "There is already in the system. <br>System message -->" + e.toString());
      result.addError(error);
      if ( employee.getId() != null ) {
        model.addAttribute("addStatus", true);
      } else {
        model.addAttribute("addStatus", false);
      }
      model.addAttribute("employee", employee);
      return commonThings(model);
    }
  }

  //If need to employee {but not applicable for this }
  @GetMapping( value = "/remove/{id}" )
  public String removeEmployee(@PathVariable Integer id) {
    employeeService.delete(id);
    return "redirect:/employee";
  }

  //To search employee any giving employee parameter
  @GetMapping( value = "/search" )
  public String search(Model model, Employee employee) {
    model.addAttribute("employeeDetail", employeeService.search(employee));
    return "employee/employee-detail";
  }
//
//    @GetMapping(value = "/age/{id}")
//    public String editEmployeeForm(@PathVariable("id") Integer id, Model model) {
//        Employee employee = employeeService.findById(id);
//
//
//        //direct age calculation
//        LocalDate l = LocalDate.of(1998, 4, 23); //specify year, month, date directly
//        LocalDate now = LocalDate.now(); //gets localDate
//        Period diff = Period.between(l, now); //difference between the dates is calculated
//        System.out.println(diff.getYears() + "years" + diff.getMonths() + "months" + diff.getDays() + "days");
//
//        //using Calendar Object
//        String s = "1994/06/23";
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
//        Date d = sdf.parse(s);
//        Calendar c = Calendar.getInstance();
//        c.setTime(d);
//        int year = c.get(Calendar.YEAR);
//        int month = c.get(Calendar.MONTH) + 1;
//        int date = c.get(Calendar.DATE);
//        LocalDate l1 = LocalDate.of(year, month, date);
//        LocalDate now1 = LocalDate.now();
//        Period diff1 = Period.between(l1, now1);
//        System.out.println("age:" + diff1.getYears() + "years");
//        model.addAttribute("employee", employee);
//        model.addAttribute("newEmployee", employee.getEpf());
//        model.addAttribute("addStatus", false);
//        model.addAttribute("contendHeader", "Employee Edit Details");
//        model.addAttribute("file", employeeFilesService.employeeFileDownloadLinks(employee));
//        return "redirect:/employee";
//    }

}

