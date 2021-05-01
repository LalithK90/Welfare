package lk.avsec_welfare.asset.death_donation.controller;

import lk.avsec_welfare.asset.dependent.controller.DependentController;
import lk.avsec_welfare.asset.dependent.entity.Dependent;
import lk.avsec_welfare.asset.dependent.entity.DependentEmployee;
import lk.avsec_welfare.asset.dependent.service.DependentEmployeeService;
import lk.avsec_welfare.asset.dependent.service.DependentService;
import lk.avsec_welfare.asset.employee.entity.Employee;
import lk.avsec_welfare.asset.dependent.entity.Enum.BenefitedNot;
import lk.avsec_welfare.asset.employee.service.EmployeeFilesService;
import lk.avsec_welfare.asset.employee.service.EmployeeService;
import lk.avsec_welfare.asset.death_donation.entity.DeathDonation;
import lk.avsec_welfare.asset.death_donation.service.DeathDonationService;
import lk.avsec_welfare.asset.main_account.entity.Enum.FundType;
import lk.avsec_welfare.asset.main_account.entity.MainAccount;
import lk.avsec_welfare.asset.main_account.service.MainAccountService;
import lk.avsec_welfare.util.service.OperatorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

@Controller
@RequestMapping( "/deathDonation" )
public class DeathDonationController {
  private final DeathDonationService deathDonationService;
  private final EmployeeService employeeService;
  private final DependentService dependentService;
  private final MainAccountService mainAccountService;
  private final EmployeeFilesService employeeFilesService;
  private final OperatorService operatorService;
  private final DependentEmployeeService dependentEmployeeService;

  public DeathDonationController(DeathDonationService deathDonationService, EmployeeService employeeService,
                                 DependentService dependentService, MainAccountService mainAccountService,
                                 EmployeeFilesService employeeFilesService, OperatorService operatorService,
                                 DependentEmployeeService dependentEmployeeService) {
    this.deathDonationService = deathDonationService;
    this.employeeService = employeeService;
    this.dependentService = dependentService;
    this.mainAccountService = mainAccountService;
    this.employeeFilesService = employeeFilesService;
    this.operatorService = operatorService;
    this.dependentEmployeeService = dependentEmployeeService;
  }

  @GetMapping
  public String findAll(Model model) {
    model.addAttribute("deathDonations", deathDonationService.findAll());
    model.addAttribute("contendHeader", "Death Donation View Details");
    return "deathDonation/deathDonation";
  }

  @GetMapping( "/{id}" )
  public String view(@PathVariable( "id" ) Integer id, Model model) {
    DeathDonation deathDonation = deathDonationService.findById(id);
    Employee employee = employeeService.findById(deathDonation.getEmployee().getId());
    Dependent dependent = dependentService.findById(deathDonation.getDependent().getId());
    model.addAttribute("deathDonationDetail", deathDonation);
    model.addAttribute("employeeDetail", employee);
    model.addAttribute("addStatus", false);
    model.addAttribute("contendHeader", "Death Donation View Details");
    model.addAttribute("files", employeeFilesService.employeeFileDownloadLinks(employee));
    model.addAttribute("dependentDetail", dependent);

    return "deathDonation/deathDonation-detail";
  }

  @GetMapping( "/add" )
  public String addForm(Model model) {
    model.addAttribute("deathDonation", new DeathDonation());
    model.addAttribute("employees", employeeService.findAll());
    model.addAttribute("addStatus", false);
    model.addAttribute("contendHeader", "Death Donation Add");
    model.addAttribute("dependentFindUrl", MvcUriComponentsBuilder
        .fromMethodName(DependentController.class, "findByEmployee", "")
        .build()
        .toString());
    return "deathDonation/addDeathDonation";
  }

  @GetMapping( "/edit/{id}" )
  public String editForm(@PathVariable( "id" ) Integer id, Model model) {
    model.addAttribute("deathDonation", deathDonationService.findById(id));
    model.addAttribute("employees", employeeService.findAll());
    model.addAttribute("addStatus", true);
    model.addAttribute("contendHeader", "Death Donation Update");
    model.addAttribute("dependentFindUrl", MvcUriComponentsBuilder
        .fromMethodName(DependentController.class, "findByEmployee", "")
        .build()
        .toString());
    return "deathDonation/addDeathDonation";
  }

  @PostMapping( value = {"/save", "/edit"} )
  public String persist(@ModelAttribute DeathDonation deathDonation, BindingResult bindingResult, Model model) {
    if ( bindingResult.hasErrors() ) {
      model.addAttribute("deathDonation", deathDonation);
      model.addAttribute("employees", employeeService.findAll());
      model.addAttribute("addStatus", true);
      model.addAttribute("dependentFindUrl", MvcUriComponentsBuilder
          .fromMethodName(DependentController.class, "findByEmployee", "")
          .build()
          .toString());
      return "deathDonation/addDeathDonation";
    }
    DeathDonation deathDonationDb = deathDonationService.persist(deathDonation);
    MainAccount mainAccount = mainAccountService.findByFundType(FundType.DEAD);
    if ( mainAccount == null ) {
      mainAccount = new MainAccount();
      mainAccount.setAmount(deathDonationDb.getAmount());
      mainAccount.setFundType(FundType.DEAD);
    } else {
      mainAccount.setAmount(operatorService.addition(deathDonationDb.getAmount(),
                                                     mainAccount.getAmount()));
    }
    mainAccountService.persist(mainAccount);

    DependentEmployee dependentEmployee =
        dependentEmployeeService.findByDependentAndEmployee(deathDonationDb.getDependent(),
                                                            deathDonationDb.getEmployee());
    dependentEmployee.setBenefitedNot(BenefitedNot.NOT);
    dependentEmployeeService.persist(dependentEmployee);

    return "redirect:/deathDonation";
  }

}
