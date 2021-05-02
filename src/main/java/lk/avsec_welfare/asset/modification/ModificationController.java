package lk.avsec_welfare.asset.modification;

import lk.avsec_welfare.asset.censure.service.CensureService;
import lk.avsec_welfare.asset.common_asset.model.TwoDate;
import lk.avsec_welfare.asset.death_donation.service.DeathDonationService;
import lk.avsec_welfare.asset.employee.service.EmployeeFilesService;
import lk.avsec_welfare.asset.employee.service.EmployeeService;
import lk.avsec_welfare.asset.grievances.service.GrievancesService;
import lk.avsec_welfare.asset.instalment.service.InstalmentService;
import lk.avsec_welfare.asset.main_account.entity.Enum.OtherFundReceivingType;
import lk.avsec_welfare.asset.main_account.service.MainAccountService;
import lk.avsec_welfare.asset.offence.service.OffenceService;
import lk.avsec_welfare.asset.other_expence.entity.OtherExpence;
import lk.avsec_welfare.asset.other_expence.entity.enums.OtherExpenseType;
import lk.avsec_welfare.asset.other_expence.service.OtherExpenceService;
import lk.avsec_welfare.asset.other_fund_receiving.entity.OtherFundReceiving;
import lk.avsec_welfare.asset.other_fund_receiving.service.OtherFundReceivingService;
import lk.avsec_welfare.asset.report.model.OtherExpenseCountAmount;
import lk.avsec_welfare.asset.report.model.OtherFundReceivingTypeAmount;
import lk.avsec_welfare.asset.userManagement.service.RoleService;
import lk.avsec_welfare.asset.userManagement.service.UserService;
import lk.avsec_welfare.asset.working_place.service.WorkingPlaceService;
import lk.avsec_welfare.util.service.DateTimeAgeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping( "/modification" )
public class ModificationController {
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

  public ModificationController(MainAccountService mainAccountService, UserService userService,
                                DeathDonationService deathDonationService, InstalmentService instalmentService,
                                OtherExpenceService otherExpenceService,
                                OtherFundReceivingService otherFundReceivingService,
                                GrievancesService grievancesService, OffenceService offenceService,
                                CensureService censureService, EmployeeFilesService employeeFilesService,
                                DateTimeAgeService dateTimeAgeService, RoleService roleService,
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

  //4. other expenses for one day and date range
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
    model.addAttribute("users", userService.findAll());
    LocalDateTime startDateTime = dateTimeAgeService.dateTimeToLocalDateStartInDay(twoDate.getStartDate());
    LocalDateTime endDateTime = dateTimeAgeService.dateTimeToLocalDateEndInDay(twoDate.getEndDate());
    List< OtherExpence > otherExpenses;
    if ( twoDate.getId() == null ) {
      otherExpenses = otherExpenceService.findByCreatedAtIsBetween(startDateTime, endDateTime);
    } else {
      otherExpenses =
          otherExpenceService.findByCreatedAtIsBetween(startDateTime, endDateTime).stream().filter(x -> x.getCreatedBy().equals(userService.findById(twoDate.getId()).getUsername())).collect(Collectors.toList());
      model.addAttribute("user", userService.findById(twoDate.getId()));
      ;
    }

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

    return "modification/otherExpense";
  }

  //2 other fund receiving for one date and date range
  @GetMapping( "/otherFundReceivingType" )
  public String donationReport(Model model) {
    LocalDate localDate = LocalDate.now();
    String message = "This report is belongs to " + localDate;
    model.addAttribute("message", message);
    return commonOtherFundReceivingType(localDate, localDate, model, null);
  }


  @PostMapping( "/otherFundReceivingType" )
  public String donationReportSearch(@ModelAttribute TwoDate twoDate, Model model) {
    String message = "This report is belongs from " + twoDate.getStartDate() + " to " + twoDate.getEndDate();
    model.addAttribute("message", message);
    System.out.println(" other fund receiving count " + twoDate.getCount());
    model.addAttribute("count", twoDate.getCount());
    return commonOtherFundReceivingType(twoDate.getStartDate(), twoDate.getEndDate(), model, twoDate.getId());
  }

  private String commonOtherFundReceivingType(LocalDate startDate, LocalDate endDate, Model model, Integer id) {

    model.addAttribute("users", userService.findAll());
    LocalDateTime startDateTime = dateTimeAgeService.dateTimeToLocalDateStartInDay(startDate);
    LocalDateTime endDateTime = dateTimeAgeService.dateTimeToLocalDateEndInDay(endDate);
    List< OtherFundReceiving > otherFundReceiving;
    if ( id == null ) {
      otherFundReceiving = otherFundReceivingService.findByCreatedAtIsBetween(startDateTime
          , endDateTime);
    } else {
      otherFundReceiving = otherFundReceivingService.findByCreatedAtIsBetween(startDateTime
          , endDateTime).stream().filter(x -> x.getCreatedBy().equals(userService.findById(id).getUsername())).collect(Collectors.toList());
      model.addAttribute("user", userService.findById(id));
    }


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

    return "modification/otherFundReceivingType";
  }
}
