package lk.avsec_welfare.asset.report;

import lk.avsec_welfare.asset.finance.death_donation.service.DeathDonationService;
import lk.avsec_welfare.asset.finance.instalment.service.InstalmentService;
import lk.avsec_welfare.asset.finance.main_account.service.MainAccountService;
import lk.avsec_welfare.asset.finance.other_expence.service.OtherExpenceService;
import lk.avsec_welfare.asset.finance.other_fund_receiving.service.OtherFundReceivingService;
import lk.avsec_welfare.asset.grievances.service.GrievancesService;
import lk.avsec_welfare.asset.userManagement.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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

  public ReportController(MainAccountService mainAccountService, UserService userService,
                          DeathDonationService deathDonationService, InstalmentService instalmentService,
                          OtherExpenceService otherExpenceService,
                          OtherFundReceivingService otherFundReceivingService, GrievancesService grievancesService) {
    this.mainAccountService = mainAccountService;
    this.userService = userService;
    this.deathDonationService = deathDonationService;
    this.instalmentService = instalmentService;
    this.otherExpenceService = otherExpenceService;
    this.otherFundReceivingService = otherFundReceivingService;
    this.grievancesService = grievancesService;
  }
  //1. main account service according to type
  //2. agent vise collection reporting
  //3. death donation
  //4. instalment versus payment
  //5. expense types  vs  amounts
  //6. other expense vs amounts
  //7. grievances vs types
}
