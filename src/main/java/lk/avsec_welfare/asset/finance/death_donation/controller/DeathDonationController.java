package lk.avsec_welfare.asset.finance.death_donation.controller;

import lk.avsec_welfare.asset.dependent.entity.Dependent;
import lk.avsec_welfare.asset.dependent.service.DependentService;
import lk.avsec_welfare.asset.employee.entity.Employee;
import lk.avsec_welfare.asset.employee.service.EmployeeFilesService;
import lk.avsec_welfare.asset.employee.service.EmployeeService;
import lk.avsec_welfare.asset.finance.death_donation.entity.DeathDonation;
import lk.avsec_welfare.asset.finance.death_donation.service.DeathDonationService;
import lk.avsec_welfare.asset.finance.main_account.service.MainAccountService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping( "/deathDonation" )
public class DeathDonationController {
  private final DeathDonationService deathDonationService;
  private final EmployeeService employeeService;
  private final DependentService dependentService;
  private final MainAccountService mainAccountService;
  private final EmployeeFilesService employeeFilesService;

  public DeathDonationController(DeathDonationService deathDonationService, EmployeeService employeeService,
                                 DependentService dependentService, MainAccountService mainAccountService,
                                 EmployeeFilesService employeeFilesService) {
    this.deathDonationService = deathDonationService;
    this.employeeService = employeeService;
    this.dependentService = dependentService;
    this.mainAccountService = mainAccountService;
    this.employeeFilesService = employeeFilesService;
  }

  @GetMapping
  public String findAll(Model model) {
    model.addAttribute("deathDonations", deathDonationService.findAll());
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
    model.addAttribute("contendHeader", "Employee View Details");
    model.addAttribute("files", employeeFilesService.employeeFileDownloadLinks(employee));
    model.addAttribute("dependentDetail", dependent);

    return "deathDonation/deathDonation-detail";
  }

  @GetMapping("/add")
  public String addForm(Model model){
    model.addAttribute("deathDonation", new DeathDonation());
    model.addAttribute("employees", employeeService.findAll());
    //todo ->
    return "deathDonation/addDeathDonation";
  }

}
