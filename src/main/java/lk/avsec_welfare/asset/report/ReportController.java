package lk.avsec_welfare.asset.report;

import lk.avsec_welfare.asset.common_asset.model.TwoDate;
import lk.avsec_welfare.asset.finance.death_donation.service.DeathDonationService;
import lk.avsec_welfare.asset.finance.instalment.service.InstalmentService;
import lk.avsec_welfare.asset.finance.main_account.entity.Enum.OtherFundReceivingType;
import lk.avsec_welfare.asset.finance.main_account.service.MainAccountService;
import lk.avsec_welfare.asset.finance.other_expence.service.OtherExpenceService;
import lk.avsec_welfare.asset.finance.other_fund_receiving.entity.OtherFundReceiving;
import lk.avsec_welfare.asset.finance.other_fund_receiving.service.OtherFundReceivingService;
import lk.avsec_welfare.asset.grievances.service.GrievancesService;
import lk.avsec_welfare.asset.report.model.OtherFundReceivingTypeAmount;
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

  public ReportController(MainAccountService mainAccountService, UserService userService,
                          DeathDonationService deathDonationService, InstalmentService instalmentService,
                          OtherExpenceService otherExpenceService,
                          OtherFundReceivingService otherFundReceivingService, GrievancesService grievancesService,
                          DateTimeAgeService dateTimeAgeService) {
    this.mainAccountService = mainAccountService;
    this.userService = userService;
    this.deathDonationService = deathDonationService;
    this.instalmentService = instalmentService;
    this.otherExpenceService = otherExpenceService;
    this.otherFundReceivingService = otherFundReceivingService;
    this.grievancesService = grievancesService;
    this.dateTimeAgeService = dateTimeAgeService;
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

  @PostMapping( "/otherFundReceivingType" )
  public String donationReportSearch(@ModelAttribute TwoDate twoDate, Model model) {
    String message = "This report is belongs from " + twoDate.getStartDate() + " to "+ twoDate.getEndDate();
    model.addAttribute("message", message);
    return commonOtherFundReceivingType(twoDate.getStartDate(), twoDate.getEndDate(),model);
  }


  //2. agent vise collection reporting
  //3. death donation
  //4. instalment versus payment
  //5. expense types  vs  amounts
  //6. other expense vs amounts
  //7. grievances vs types
}
