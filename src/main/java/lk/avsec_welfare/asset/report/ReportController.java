package lk.avsec_welfare.asset.report;

import lk.avsec_welfare.asset.censure.service.CensureService;
import lk.avsec_welfare.asset.common_asset.model.TwoDate;
import lk.avsec_welfare.asset.common_asset.model.enums.LiveDead;
import lk.avsec_welfare.asset.employee.entity.Employee;
import lk.avsec_welfare.asset.employee.service.EmployeeFilesService;
import lk.avsec_welfare.asset.employee.service.EmployeeService;
import lk.avsec_welfare.asset.death_donation.entity.DeathDonation;
import lk.avsec_welfare.asset.death_donation.service.DeathDonationService;
import lk.avsec_welfare.asset.instalment.entity.Instalment;
import lk.avsec_welfare.asset.instalment.service.InstalmentService;
import lk.avsec_welfare.asset.main_account.entity.Enum.FundType;
import lk.avsec_welfare.asset.main_account.entity.Enum.OtherFundReceivingType;
import lk.avsec_welfare.asset.main_account.service.MainAccountService;
import lk.avsec_welfare.asset.other_expence.entity.OtherExpence;
import lk.avsec_welfare.asset.other_expence.entity.enums.OtherExpenseType;
import lk.avsec_welfare.asset.other_expence.service.OtherExpenceService;
import lk.avsec_welfare.asset.other_fund_receiving.entity.OtherFundReceiving;
import lk.avsec_welfare.asset.other_fund_receiving.service.OtherFundReceivingService;
import lk.avsec_welfare.asset.grievances.service.GrievancesService;
import lk.avsec_welfare.asset.offence.entity.Offence;

import lk.avsec_welfare.asset.offence.service.OffenceService;
import lk.avsec_welfare.asset.report.model.AgentTotalAmount;
import lk.avsec_welfare.asset.report.model.OtherExpenseCountAmount;
import lk.avsec_welfare.asset.report.model.OtherFundReceivingTypeAmount;
import lk.avsec_welfare.asset.report.model.SectionEmployeeInstalmentAmount;
import lk.avsec_welfare.asset.userManagement.entity.Role;
import lk.avsec_welfare.asset.userManagement.entity.User;
import lk.avsec_welfare.asset.userManagement.service.RoleService;
import lk.avsec_welfare.asset.userManagement.service.UserService;
import lk.avsec_welfare.asset.working_place.controller.WorkingPlaceController;
import lk.avsec_welfare.asset.working_place.entity.WorkingPlace;
import lk.avsec_welfare.asset.working_place.entity.enums.WorkingPlaceSection;
import lk.avsec_welfare.asset.working_place.service.WorkingPlaceService;
import lk.avsec_welfare.util.service.DateTimeAgeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping( "/report" )
public class ReportController {
  private final MainAccountService mainAccountService;
  private final UserService userService;
  private final DeathDonationService deathDonationService;
  private final InstalmentService instalmentService;
  private final OtherExpenceService otherExpenceService;
  private final OtherFundReceivingService otherFundReceivingService;
  private final GrievancesService grievancesService;
  private final OffenceService offenceService;
  private final CensureService censureService;
  private final EmployeeFilesService employeeFilesService;
  private final DateTimeAgeService dateTimeAgeService;
  private final RoleService roleService;
  private final EmployeeService employeeService;
  private final WorkingPlaceService workingPlaceService;

  public ReportController(MainAccountService mainAccountService, UserService userService,
                          DeathDonationService deathDonationService, InstalmentService instalmentService,
                          OtherExpenceService otherExpenceService,
                          OtherFundReceivingService otherFundReceivingService, GrievancesService grievancesService,
                          OffenceService offenceService, CensureService censureService,
                          EmployeeFilesService employeeFilesService, DateTimeAgeService dateTimeAgeService,
                          RoleService roleService,
                          EmployeeService employeeService, WorkingPlaceService workingPlaceService) {
    this.mainAccountService = mainAccountService;
    this.userService = userService;
    this.deathDonationService = deathDonationService;
    this.instalmentService = instalmentService;
    this.otherExpenceService = otherExpenceService;
    this.otherFundReceivingService = otherFundReceivingService;
    this.grievancesService = grievancesService;
    this.offenceService = offenceService;
    this.censureService = censureService;
    this.employeeFilesService = employeeFilesService;

    this.dateTimeAgeService = dateTimeAgeService;
    this.roleService = roleService;
    this.employeeService = employeeService;
    this.workingPlaceService = workingPlaceService;
  }

  //1. main account service according to type
  @GetMapping( "/mainAccount" )
  public String mainAccountReport(Model model) {
    List< BigDecimal > instalmentAmount = new ArrayList<>();
    mainAccountService.findAll()
        .stream()
        .filter(x -> x.getFundType().equals(FundType.INSTALMENTS))
        .collect(Collectors.toList()).forEach(x -> instalmentAmount.add(x.getAmount()));
    model.addAttribute("instalmentAmount", instalmentAmount.stream().reduce(BigDecimal.ZERO, BigDecimal::add));

    List< BigDecimal > otherFundAmount = new ArrayList<>();
    mainAccountService.findAll()
        .stream()
        .filter(x -> x.getFundType().equals(FundType.OTHER_FUN_RE))
        .collect(Collectors.toList()).forEach(x -> otherFundAmount.add(x.getAmount()));
    model.addAttribute("otherFundAmount", otherFundAmount.stream().reduce(BigDecimal.ZERO, BigDecimal::add));

    List< BigDecimal > otherExpenseAmount = new ArrayList<>();
    mainAccountService.findAll()
        .stream()
        .filter(x -> x.getFundType().equals(FundType.OTHEXP))
        .collect(Collectors.toList()).forEach(x -> otherExpenseAmount.add(x.getAmount()));
    model.addAttribute("otherExpenseAmount", otherExpenseAmount.stream().reduce(BigDecimal.ZERO, BigDecimal::add));

    List< BigDecimal > deathAmount = new ArrayList<>();
    mainAccountService.findAll()
        .stream()
        .filter(x -> x.getFundType().equals(FundType.DEAD))
        .collect(Collectors.toList()).forEach(x -> deathAmount.add(x.getAmount()));
    model.addAttribute("deathAmount", deathAmount.stream().reduce(BigDecimal.ZERO, BigDecimal::add));


    return "report/mainAccount";
  }

  //2 other fund receiving for one date and date range
  @GetMapping( "/otherFundReceivingType" )
  public String donationReport(Model model) {
    LocalDate localDate = LocalDate.now();
    String message = "This report is belongs to " + localDate;
    model.addAttribute("message", message);
    return commonOtherFundReceivingType(localDate, localDate, model);
  }


  @PostMapping( "/otherFundReceivingType" )
  public String donationReportSearch(@ModelAttribute TwoDate twoDate, Model model) {
    String message = "This report is belongs from " + twoDate.getStartDate() + " to " + twoDate.getEndDate();
    model.addAttribute("message", message);
    return commonOtherFundReceivingType(twoDate.getStartDate(), twoDate.getEndDate(), model);
  }

  private String commonOtherFundReceivingType(LocalDate startDate, LocalDate endDate, Model model) {
    LocalDateTime startDateTime = dateTimeAgeService.dateTimeToLocalDateStartInDay(startDate);
    LocalDateTime endDateTime = dateTimeAgeService.dateTimeToLocalDateEndInDay(endDate);

    List< OtherFundReceiving > otherFundReceiving = otherFundReceivingService.findByCreatedAtIsBetween(startDateTime
        , endDateTime);


    List< OtherFundReceivingTypeAmount > otherFundReceivingTypeAmounts = new ArrayList<>();

    for ( OtherFundReceivingType otherFundReceivingType : OtherFundReceivingType.values() ) {
      OtherFundReceivingTypeAmount otherFundReceivingTypeAmount = new OtherFundReceivingTypeAmount();
      List< BigDecimal > amounts = new ArrayList<>();

      List< OtherFundReceiving > otherFundReceivingAccordingTo = otherFundReceiving
          .stream()
          .filter(x -> x.getOtherFundReceivingType().equals(otherFundReceivingType))
          .collect(Collectors.toList());

      otherFundReceivingTypeAmount.setOtherFundReceivingType(otherFundReceivingType);
      otherFundReceivingTypeAmount.setRecordCount(otherFundReceivingAccordingTo.size());
      otherFundReceivingAccordingTo.forEach(x -> amounts.add(x.getAmount()));
      otherFundReceivingTypeAmount.setAmount(amounts.stream().reduce(BigDecimal.ZERO, BigDecimal::add));

      otherFundReceivingTypeAmounts.add(otherFundReceivingTypeAmount);

    }
    model.addAttribute("otherFundReceivingTypeAmounts", otherFundReceivingTypeAmounts);

    return "report/otherFundReceivingType";
  }

  //3. death donation for one day and date range
  @GetMapping( "/deathDonation" )
  public String deathDonation(Model model) {
    LocalDate localDate = LocalDate.now();
    String message = "This report is belongs to " + localDate;
    TwoDate twoDate = new TwoDate();
    twoDate.setEndDate(localDate);
    twoDate.setStartDate(localDate);

    return commonDeathDonation(twoDate, model, message);
  }

  @PostMapping( "/deathDonation" )
  public String deathDonationSearch(@ModelAttribute TwoDate twoDate, Model model) {
    String message = "This report is belongs from " + twoDate.getStartDate() + " to " + twoDate.getEndDate();
    model.addAttribute("message", message);
    return commonDeathDonation(twoDate, model, message);
  }

  private String commonDeathDonation(TwoDate twoDate, Model model, String message) {
    LocalDateTime startDateTime = dateTimeAgeService.dateTimeToLocalDateStartInDay(twoDate.getStartDate());
    LocalDateTime endDateTime = dateTimeAgeService.dateTimeToLocalDateEndInDay(twoDate.getEndDate());

    List< DeathDonation > deathDonations = deathDonationService.findByCreatedAtIsBetween(startDateTime, endDateTime);

    if ( twoDate.getId() == null ) {
      model.addAttribute("deathDonations", deathDonations);
    } else {
      Employee employee = employeeService.findById(twoDate.getId());
      model.addAttribute("deathDonations",
                         deathDonations.stream().filter(x -> x.getEmployee().equals(employee)).collect(Collectors.toList()));
      model.addAttribute("employeeDetail", employee);
      message =
          message + " \n This report is belongs to epf number " + employee.getEpf() + " name " + employee.getName();
    }
    model.addAttribute("message", message);
    model.addAttribute("employees", employeeService.findAll());
    return "report/deathDonation";
  }


  //4. other expences for one day and date range
  @GetMapping( "/otherExpense" )
  public String otherExpense(Model model) {
    LocalDate localDate = LocalDate.now();
    String message = "This report is belongs to " + localDate;
    model.addAttribute("message", message);
    TwoDate twoDate = new TwoDate();
    twoDate.setEndDate(localDate);
    twoDate.setStartDate(localDate);
    return commonOtherExpense(twoDate, model);
  }


  @PostMapping( "/otherExpense" )
  public String otherExpenseSearch(@ModelAttribute TwoDate twoDate, Model model) {
    String message = "This report is belongs from " + twoDate.getStartDate() + " to " + twoDate.getEndDate();
    model.addAttribute("message", message);
    return commonOtherExpense(twoDate, model);
  }

  private String commonOtherExpense(TwoDate twoDate, Model model) {
    LocalDateTime startDateTime = dateTimeAgeService.dateTimeToLocalDateStartInDay(twoDate.getStartDate());
    LocalDateTime endDateTime = dateTimeAgeService.dateTimeToLocalDateEndInDay(twoDate.getEndDate());


    List< OtherExpence > otherExpenses = otherExpenceService.findByCreatedAtIsBetween(startDateTime, endDateTime);
    List< OtherExpenseCountAmount > otherExpenseCountAmounts = new ArrayList<>();
    for ( OtherExpenseType otherExpenseType : OtherExpenseType.values() ) {
      OtherExpenseCountAmount otherExpenseCountAmount = new OtherExpenseCountAmount();
      otherExpenseCountAmount.setOtherExpenseType(otherExpenseType);
      List< OtherExpence > otherExpenseByOtherExpenseType =
          otherExpenses.stream().filter(x -> x.getOtherExpenseType().equals(otherExpenseType)).collect(Collectors.toList());
      otherExpenseCountAmount.setRecordCounter(otherExpenseByOtherExpenseType.size());
      List< BigDecimal > otherExpenceByOtherExpenseTypeAmount = new ArrayList<>();

      otherExpenseByOtherExpenseType.forEach(x -> otherExpenceByOtherExpenseTypeAmount.add(x.getAmount()));
      otherExpenseCountAmount.setAmount(otherExpenceByOtherExpenseTypeAmount.stream().reduce(BigDecimal.ZERO,
                                                                                             BigDecimal::add));
      otherExpenseCountAmounts.add(otherExpenseCountAmount);

    }


    model.addAttribute("otherExpenseCountAmounts", otherExpenseCountAmounts);

    return "report/otherExpense";
  }


//5. agent vise collection reporting date and date range

  @GetMapping( "/agentVise" )
  public String agentVise(Model model) {
    LocalDate localDate = LocalDate.now();
    String message = "This report is belongs to " + localDate;
    model.addAttribute("message", message);
    return commonAgentVise(localDate, localDate, model);
  }


  @PostMapping( "/agentVise" )
  public String agentViseSearch(@ModelAttribute TwoDate twoDate, Model model) {
    String message = "This report is belongs from " + twoDate.getStartDate() + " to " + twoDate.getEndDate();
    model.addAttribute("message", message);
    return commonAgentVise(twoDate.getStartDate(), twoDate.getEndDate(), model);
  }

  private String commonAgentVise(LocalDate startDate, LocalDate endDate, Model model) {
    LocalDateTime startDateTime = dateTimeAgeService.dateTimeToLocalDateStartInDay(startDate);
    LocalDateTime endDateTime = dateTimeAgeService.dateTimeToLocalDateEndInDay(endDate);

    Role role = roleService.findByRoleName("AGENT");
    List< User > users = role.getUsers();

    List< AgentTotalAmount > agentTotalAmounts = new ArrayList<>();

    users.forEach(x -> {
      AgentTotalAmount agentTotalAmount = new AgentTotalAmount();
      List< BigDecimal > agentAmount = new ArrayList<>();
      Employee employee = userService.findByUserName(x.getUsername()).getEmployee();

      instalmentService.findByCreatedAtIsBetweenAndCreatedBy(startDateTime, endDateTime, x.getUsername()).forEach(y -> {
        agentAmount.add(y.getAmount());
      });

      agentTotalAmount.setEmployee(employee);
      agentTotalAmount.setCount(agentAmount.size());
      agentTotalAmount.setAmount(agentAmount.stream().reduce(BigDecimal.ZERO, BigDecimal::add));

      agentTotalAmounts.add(agentTotalAmount);
    });


    model.addAttribute("agentTotalAmounts", agentTotalAmounts);

    return "report/agent";
  }


  //1.total Employee up to now
  @GetMapping( "/employee" )
  public String allEmployeeReport(Model model) {
    model.addAttribute("allEmployees", employeeService.findAll());
    return "report/allEmployee";
  }


  @PostMapping( "/employeeAllCount" )
  public String employeeAllCountSearch(@ModelAttribute TwoDate twoDate, Model model) {
    return commonEmployeeAllCount(model,
                                  employeeService.findByCreatedAtBetween(dateTimeAgeService.dateTimeToLocalDateStartInDay(twoDate.getStartDate()), dateTimeAgeService.dateTimeToLocalDateEndInDay(twoDate.getEndDate())));
  }

  private String commonEmployeeAllCount(Model model, List< Employee > employeesRequest) {
    model.addAttribute("twoDate", new TwoDate());
    List< Employee > employees = new ArrayList<>();
    for ( Employee employee : employeesRequest
        .stream()
        .filter(x -> LiveDead.ACTIVE.equals(x.getLiveDead()))
        .collect(Collectors.toList())
    ) {
      employee.setFileInfo(employeeFilesService.employeeFileDownloadLinks(employee));
      employees.add(employee);
    }
    model.addAttribute("employees", employees);
    return "report/employeeAllCount";
  }


  //  offence and employee count
  @GetMapping( "/offenceCount" )
  public String offenceEmployee(Model model) {
    model.addAttribute("offences", offenceService.findAll());
    return "report/offenceReport";
  }

  @GetMapping( "/offenceCount/{id}" )
  public String offenceEmployeeSearch(@PathVariable( "id" ) Integer id, Model model) {
    List< Employee > employees = new ArrayList<>();
    Offence offence = offenceService.findById(id);
    model.addAttribute("offenceDetail", offence);
    censureService.findByOffence(offence).forEach(x -> employees.add(x.getEmployee()));
    model.addAttribute("employees", employees);
    return "report/offenceEmployee";
  }

  // section employee instalment amount
  @GetMapping( "/sectionEmployeeInstalmentAmount" )
  public String sectionEmployeeInstalmentAmount(Model model) {
    model.addAttribute("workingPlaceSections", WorkingPlaceSection.values());
    model.addAttribute("workingPlaceFindUrl", MvcUriComponentsBuilder
        .fromMethodName(WorkingPlaceController.class, "findBySection", "")
        .build()
        .toString());
    return "report/sectionEmployee";
  }

  @PostMapping( "/sectionEmployeeInstalmentAmount" )
  public String sectionEmployeeInstalmentAmount(@ModelAttribute TwoDate twoDate, Model model) {
    System.out.println("come here "+ twoDate.toString());
    model.addAttribute("workingPlaceSections", WorkingPlaceSection.values());
    LocalDateTime startDateTime = dateTimeAgeService.dateTimeToLocalDateStartInDay(twoDate.getStartDate());
    LocalDateTime endDateTime = dateTimeAgeService.dateTimeToLocalDateEndInDay(twoDate.getEndDate());

    WorkingPlace workingPlace = workingPlaceService.findById(twoDate.getId());

    List< Instalment > instalments = instalmentService.findByCreatedAtIsBetween(startDateTime, endDateTime);

    HashSet< Instalment > instalmentsAccordingToWorkingPlaceSection = new HashSet<>();
    instalments.forEach(x -> {
      Employee employeeDB = employeeService.findById(x.getEmployee().getId());
      WorkingPlace workingPlaceDb = workingPlaceService.findById(employeeDB.getWorkingPlace().getId());
      if ( workingPlaceDb != null && employeeDB != null ) {
        if ( workingPlaceDb.getWorkingPlaceSection().equals(workingPlace.getWorkingPlaceSection()) ) {
          instalmentsAccordingToWorkingPlaceSection.add(x);
        }
      }
    });
    List< SectionEmployeeInstalmentAmount > sectionEmployeeInstalmentAmounts = new ArrayList<>();

    instalmentsAccordingToWorkingPlaceSection.forEach(x -> {
      WorkingPlace workingPlaceDb = workingPlaceService.findById(x.getEmployee().getWorkingPlace().getId());
      SectionEmployeeInstalmentAmount sectionEmployeeInstalmentAmount = new SectionEmployeeInstalmentAmount();
      sectionEmployeeInstalmentAmount.setWorkingPlaceSection(workingPlaceDb.getWorkingPlaceSection());
      Employee employeeDb = employeeService.findById(x.getEmployee().getId());
      sectionEmployeeInstalmentAmount.setEmployee(employeeDb);

      List< BigDecimal > employeePaidTotal = new ArrayList<>();

      instalments
          .stream()
          .filter(z -> z.getEmployee().equals(employeeDb))
          .collect(Collectors.toList())
          .forEach(y -> employeePaidTotal.add(y.getAmount()));
      sectionEmployeeInstalmentAmount.setInstalmentCount(employeePaidTotal.size());
      sectionEmployeeInstalmentAmount.setAmount(employeePaidTotal.stream().reduce(BigDecimal.ZERO, BigDecimal::add));

      sectionEmployeeInstalmentAmounts.add(sectionEmployeeInstalmentAmount);

    });
    model.addAttribute("sectionEmployeeInstalmentAmounts", sectionEmployeeInstalmentAmounts);

    return "report/sectionEmployee";
  }
}
