package lk.avsec_welfare.asset.finance.instalment.controller;

import lk.avsec_welfare.asset.employee.entity.Employee;
import lk.avsec_welfare.asset.employee.entity.enums.WelfarePosition;
import lk.avsec_welfare.asset.employee.service.EmployeeService;
import lk.avsec_welfare.asset.finance.entity.Enum.FundType;
import lk.avsec_welfare.asset.finance.entity.Enum.OtherFundReceivingType;
import lk.avsec_welfare.asset.finance.entity.MainAccount;
import lk.avsec_welfare.asset.finance.instalment.entity.Instalment;
import lk.avsec_welfare.asset.finance.instalment.service.InstalmentService;
import lk.avsec_welfare.asset.finance.installment_type.service.InstalmentTypeService;
import lk.avsec_welfare.asset.finance.service.MainAccountService;
import lk.avsec_welfare.asset.finance.instalment.commonModel.YearAndPaidAmount;
import lk.avsec_welfare.asset.userManagement.entity.User;
import lk.avsec_welfare.asset.userManagement.service.UserService;
import lk.avsec_welfare.asset.working_place.entity.WorkingPlace;
import lk.avsec_welfare.asset.working_place.service.WorkingPlaceService;
import lk.avsec_welfare.util.service.OperatorService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping( "/collection" )
public class CollectionController {
  private final EmployeeService employeeService;
  private final InstalmentTypeService instalmentTypeService;
  private final InstalmentService instalmentService;
  private final WorkingPlaceService workingPlaceService;
  private final MainAccountService mainAccountService;
  private final UserService userService;
  private final OperatorService operatorService;


  public CollectionController(EmployeeService employeeService, InstalmentTypeService instalmentTypeService,
                              InstalmentService instalmentService, WorkingPlaceService workingPlaceService,
                              MainAccountService mainAccountService,
                              UserService userService, OperatorService operatorService) {
    this.employeeService = employeeService;
    this.instalmentTypeService = instalmentTypeService;
    this.instalmentService = instalmentService;
    this.workingPlaceService = workingPlaceService;
    this.mainAccountService = mainAccountService;
    this.userService = userService;
    this.operatorService = operatorService;
  }

  @GetMapping
  public String allEmployee(Model model) {
    User user =
        userService.findById(userService.findByUserIdByUserName(SecurityContextHolder.getContext().getAuthentication().getName()));
    WorkingPlace workingPlace = workingPlaceService.findById(user.getEmployee().getWorkingPlace().getId());
    WelfarePosition welfarePosition = user.getEmployee().getWelfarePosition();
    if ( welfarePosition != null ) {
      //member and other person can not view employee who need to pay
      if ( welfarePosition.equals(WelfarePosition.OTR) || welfarePosition.equals(WelfarePosition.MBR) ) {
        model.addAttribute("message", "You have no permission to see this");
      }
      if ( welfarePosition.equals(WelfarePosition.AGT) ) {
        List< Employee > employees = employeeService.findAll()
            .stream()
            .filter((x) -> x.getWorkingPlace().equals(workingPlace))
            .collect(Collectors.toList());
        model.addAttribute("employees", employees);
      } else {
        model.addAttribute("employees", employeeService.findAll());
      }
    }
    return "processManagement/allEmployee";
  }

  @GetMapping( "/{id}" )
  public String employeePayment(@PathVariable Integer id, Model model) {
    Employee employee = employeeService.findById(id);
    //todo need to find payment history and available balance need show
    List< Instalment > paidInstalments = instalmentService.findByEmployee(employee);
    List< String > paidYears = new ArrayList<>();
    //set all years on employee must pay
    paidInstalments.forEach((x) -> {
      paidYears.add(x.getInstalmentType().getYear());
    });
    //remove duplicate years on paidYears array
    paidYears.stream().distinct().collect(Collectors.toList());
    //year and paid amount
    List< YearAndPaidAmount > yearAndPaidAmounts = new ArrayList<>();
    //Map<Year,BigDecimal>
    //need to create year and total payment for particular year
    for ( String paidYear : paidYears ) {
      BigDecimal paidAmountForYear = BigDecimal.ZERO;
      BigDecimal yearAmount = BigDecimal.ZERO;
      for ( Instalment paidInstalment : paidInstalments ) {
        if ( paidInstalment.getInstalmentType().getYear().equals(paidYear) ) {
          yearAmount = paidInstalment.getAmount();
          paidAmountForYear = operatorService.addition(paidAmountForYear, paidInstalment.getAmount());
        }
      }
      YearAndPaidAmount yearAndPaidAmount = new YearAndPaidAmount();
      yearAndPaidAmount.setPaidAmount(paidAmountForYear);
      yearAndPaidAmount.setYear(paidYear);
      yearAndPaidAmount.setYearAmount(yearAmount);
      yearAndPaidAmount.setPendingAmount(operatorService.subtraction(yearAmount, paidAmountForYear));
      yearAndPaidAmounts.add(yearAndPaidAmount);
    }
    model.addAttribute("yearAndPaidAmounts", yearAndPaidAmounts);
    model.addAttribute("employeeDetail", employee);
    model.addAttribute("instalment", new Instalment());
    model.addAttribute("instalments", instalmentService.findByEmployee(employee));
    model.addAttribute("instalmentTypes", instalmentTypeService.findAll());
    return "processManagement/addInstalment";
  }

  @PostMapping( "/save" )
  public String saveInstalment(@Valid @ModelAttribute Instalment instalment, BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {
    if ( bindingResult.hasErrors() ) {
      return "redirect:/collection/".concat(String.valueOf(instalment.getEmployee().getId()));
    }
    Instalment instalmentDb = instalmentService.persist(instalment);
    MainAccount mainAccount = mainAccountService.findByFundType(FundType.INSTALMENTS);
    if ( mainAccount == null ) {
      mainAccount = new MainAccount();
      mainAccount.setAmount(instalmentDb.getAmount());
      mainAccount.setFundType(FundType.INSTALMENTS);
    } else {
      mainAccount.setAmount(operatorService.addition(instalmentDb.getAmount(), mainAccount.getAmount()));
    }
    mainAccountService.persist(mainAccount);
    return "redirect:/collection";
  }

}
