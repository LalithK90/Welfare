package lk.avsec_welfare.asset.modification;

import lk.avsec_welfare.asset.censure.service.CensureService;
import lk.avsec_welfare.asset.death_donation.service.DeathDonationService;
import lk.avsec_welfare.asset.employee.service.EmployeeFilesService;
import lk.avsec_welfare.asset.employee.service.EmployeeService;
import lk.avsec_welfare.asset.grievances.service.GrievancesService;
import lk.avsec_welfare.asset.instalment.service.InstalmentService;
import lk.avsec_welfare.asset.main_account.service.MainAccountService;
import lk.avsec_welfare.asset.offence.service.OffenceService;
import lk.avsec_welfare.asset.other_expence.service.OtherExpenceService;
import lk.avsec_welfare.asset.other_fund_receiving.service.OtherFundReceivingService;
import lk.avsec_welfare.asset.userManagement.service.RoleService;
import lk.avsec_welfare.asset.userManagement.service.UserService;
import lk.avsec_welfare.asset.working_place.service.WorkingPlaceService;
import lk.avsec_welfare.util.service.DateTimeAgeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

  @GetMapping
  public String modification(Model model) {

    return "modification/modification";
  }
}
