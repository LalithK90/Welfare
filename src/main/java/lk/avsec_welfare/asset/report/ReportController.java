package lk.avsec_welfare.asset.report;

import lk.avsec_welfare.asset.common_asset.model.TwoDate;
import lk.avsec_welfare.asset.finance.death_donation.entity.DeathDonation;
import lk.avsec_welfare.asset.finance.death_donation.service.DeathDonationService;
import lk.avsec_welfare.asset.finance.instalment.service.InstalmentService;
import lk.avsec_welfare.asset.finance.main_account.entity.Enum.OtherFundReceivingType;
import lk.avsec_welfare.asset.finance.main_account.service.MainAccountService;
import lk.avsec_welfare.asset.finance.other_expence.service.OtherExpenceService;
import lk.avsec_welfare.asset.finance.other_fund_receiving.entity.OtherFundReceiving;
import lk.avsec_welfare.asset.finance.other_fund_receiving.service.OtherFundReceivingService;
import lk.avsec_welfare.asset.grievances.service.GrievancesService;
import lk.avsec_welfare.asset.report.model.OtherFundReceivingTypeAmount;
import lk.avsec_welfare.asset.userManagement.entity.Role;
import lk.avsec_welfare.asset.userManagement.entity.User;
import lk.avsec_welfare.asset.userManagement.service.RoleService;
import lk.avsec_welfare.asset.userManagement.service.UserService;
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
@RequestMapping( "/report" )
public class ReportController {
  private final MainAccountService mainAccountService;
  private final UserService userService;
  private final DeathDonationService deathDonationService;
  private final InstalmentService instalmentService;
  private final OtherExpenceService otherExpenceService;
  private final OtherFundReceivingService otherFundReceivingService;
  private final GrievancesService grievancesService;
  private final DateTimeAgeService dateTimeAgeService;
  private final RoleService roleService;

  public ReportController(MainAccountService mainAccountService, UserService userService,
                          DeathDonationService deathDonationService, InstalmentService instalmentService,
                          OtherExpenceService otherExpenceService,
                          OtherFundReceivingService otherFundReceivingService, GrievancesService grievancesService,
                          DateTimeAgeService dateTimeAgeService, RoleService roleService) {
    this.mainAccountService = mainAccountService;
    this.userService = userService;
    this.deathDonationService = deathDonationService;
    this.instalmentService = instalmentService;
    this.otherExpenceService = otherExpenceService;
    this.otherFundReceivingService = otherFundReceivingService;
    this.grievancesService = grievancesService;
    this.dateTimeAgeService = dateTimeAgeService;
    this.roleService = roleService;
  }

  //1. main account service according to type
  @GetMapping( "/mainAccount" )
  public String mainAccountReport(Model model) {
    model.addAttribute("mainAccounts", mainAccountService.findAll());
    return "report/mainAccount";
  }


  @GetMapping( "/otherFundReceivingType" )
  public String donationReport(Model model) {
    LocalDate localDate = LocalDate.now();
    String message = "This report is belongs to " + localDate;
    model.addAttribute("message", message);
    return commonOtherFundReceivingType(localDate, localDate,model);
  }


  @PostMapping( "/otherFundReceivingType" )
  public String donationReportSearch(@ModelAttribute TwoDate twoDate, Model model) {
    String message = "This report is belongs from " + twoDate.getStartDate() + " to "+ twoDate.getEndDate();
    model.addAttribute("message", message);
    return commonOtherFundReceivingType(twoDate.getStartDate(), twoDate.getEndDate(),model);
  }

  private String commonOtherFundReceivingType(LocalDate startDate, LocalDate endDate, Model model) {
    LocalDateTime startDateTime = dateTimeAgeService.dateTimeToLocalDateStartInDay(startDate);
    LocalDateTime endDateTime = dateTimeAgeService.dateTimeToLocalDateEndInDay(endDate);

    List< OtherFundReceiving > otherFundReceivings = otherFundReceivingService.findByCreatedAtIsBetween(startDateTime
        , endDateTime);


    List< OtherFundReceivingTypeAmount > otherFundReceivingTypeAmounts = new ArrayList<>();

    for ( OtherFundReceivingType otherFundReceivingType : OtherFundReceivingType.values() ) {
      OtherFundReceivingTypeAmount otherFundReceivingTypeAmount = new OtherFundReceivingTypeAmount();
      List< BigDecimal > amounts = new ArrayList<>();

      List< OtherFundReceiving > otherFundReceivingAccordingsTo = otherFundReceivings
          .stream()
          .filter(x -> x.getOtherFundReceivingType().equals(otherFundReceivingType))
          .collect(Collectors.toList());

      otherFundReceivingTypeAmount.setOtherFundReceivingType(otherFundReceivingType);
      otherFundReceivingTypeAmount.setRecordCount(otherFundReceivingAccordingsTo.size());
      otherFundReceivingAccordingsTo.forEach(x -> amounts.add(x.getAmount()));
      otherFundReceivingTypeAmount.setAmount(amounts.stream().reduce(BigDecimal.ZERO, BigDecimal::add));

      otherFundReceivingTypeAmounts.add(otherFundReceivingTypeAmount);

    }
    model.addAttribute("otherFundReceivingTypeAmounts", otherFundReceivingTypeAmounts);

    return "report/otherFundReceivingType";
  }

  //2. agent vise collection reporting

  @GetMapping( "/agentVise" )
  public String agentVise(Model model) {
    LocalDate localDate = LocalDate.now();
    String message = "This report is belongs to " + localDate;
    model.addAttribute("message", message);
    return commonAgentVise(localDate, localDate,model);
  }


  @PostMapping( "/agentVise" )
  public String agentViseSearch(@ModelAttribute TwoDate twoDate, Model model) {
    String message = "This report is belongs from " + twoDate.getStartDate() + " to "+ twoDate.getEndDate();
    model.addAttribute("message", message);
    return commonAgentVise(twoDate.getStartDate(), twoDate.getEndDate(),model);
  }

  private String commonAgentVise(LocalDate startDate, LocalDate endDate, Model model) {
    LocalDateTime startDateTime = dateTimeAgeService.dateTimeToLocalDateStartInDay(startDate);
    LocalDateTime endDateTime = dateTimeAgeService.dateTimeToLocalDateEndInDay(endDate);

    Role role = roleService.findByRoleName("AGENT");
    List< User > users = role.getUsers();

//todo



  //  model.addAttribute("otherFundReceivingTypeAmounts", otherFundReceivingTypeAmounts);

    return "report/agent";
  }

  //3. death donation
  @GetMapping( "/deathDonation" )
  public String deathDonation(Model model) {
    LocalDate localDate = LocalDate.now();
    String message = "This report is belongs to " + localDate;
    model.addAttribute("message", message);
    return commonDeathDonation(localDate, localDate,model);
  }


  @PostMapping( "/deathDonation" )
  public String deathDonationSearch(@ModelAttribute TwoDate twoDate, Model model) {
    String message = "This report is belongs from " + twoDate.getStartDate() + " to "+ twoDate.getEndDate();
    model.addAttribute("message", message);
    return commonDeathDonation(twoDate.getStartDate(), twoDate.getEndDate(),model);
  }

  private String commonDeathDonation(LocalDate startDate, LocalDate endDate, Model model) {
    LocalDateTime startDateTime = dateTimeAgeService.dateTimeToLocalDateStartInDay(startDate);
    LocalDateTime endDateTime = dateTimeAgeService.dateTimeToLocalDateEndInDay(endDate);

    List< DeathDonation > deathDonations = deathDonationService.findByCreatedAtIsBetween(startDateTime, endDateTime);

  //  model.addAttribute("otherFundReceivingTypeAmounts", otherFundReceivingTypeAmounts);

    model.addAttribute
    return "report/otherFundReceivingType";
  }

  //5. expense types  vs  amounts
  @GetMapping( "/otherExpense" )
  public String otherExpense(Model model) {
    LocalDate localDate = LocalDate.now();
    String message = "This report is belongs to " + localDate;
    model.addAttribute("message", message);
    return commonDeathDonation(localDate, localDate,model);
  }


  @PostMapping( "/otherExpense" )
  public String otherExpenseSearch(@ModelAttribute TwoDate twoDate, Model model) {
    String message = "This report is belongs from " + twoDate.getStartDate() + " to "+ twoDate.getEndDate();
    model.addAttribute("message", message);
    return commonDeathDonation(twoDate.getStartDate(), twoDate.getEndDate(),model);
  }

  private String commonOtherExpense(LocalDate startDate, LocalDate endDate, Model model) {
    LocalDateTime startDateTime = dateTimeAgeService.dateTimeToLocalDateStartInDay(startDate);
    LocalDateTime endDateTime = dateTimeAgeService.dateTimeToLocalDateEndInDay(endDate);

    Role role = roleService.findByRoleName("AGENT");
    List< User > users = role.getUsers();
//todo

//    model.addAttribute("otherFundReceivingTypeAmounts", otherFundReceivingTypeAmounts);

    return "report/otherFundReceivingType";
  }


}
