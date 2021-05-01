package lk.avsec_welfare.asset.instalment.controller;

import lk.avsec_welfare.asset.employee.entity.Employee;
import lk.avsec_welfare.asset.employee.entity.enums.BoardOfDirectors;
import lk.avsec_welfare.asset.employee.entity.enums.EmployeeStatus;
import lk.avsec_welfare.asset.employee.entity.enums.WelfarePosition;
import lk.avsec_welfare.asset.employee.service.EmployeeService;
import lk.avsec_welfare.asset.main_account.entity.Enum.CollectionType;
import lk.avsec_welfare.asset.main_account.entity.Enum.FundType;
import lk.avsec_welfare.asset.main_account.entity.MainAccount;
import lk.avsec_welfare.asset.instalment.commonModel.InstalmentApprove;
import lk.avsec_welfare.asset.instalment.commonModel.InstalmentTreasure;
import lk.avsec_welfare.asset.instalment.entity.Instalment;
import lk.avsec_welfare.asset.instalment.entity.enums.InstalmentStatus;
import lk.avsec_welfare.asset.instalment.service.InstalmentService;
import lk.avsec_welfare.asset.installment_type.service.InstalmentTypeService;
import lk.avsec_welfare.asset.main_account.service.MainAccountService;
import lk.avsec_welfare.asset.instalment.commonModel.YearAndPaidAmount;
import lk.avsec_welfare.asset.userManagement.entity.User;
import lk.avsec_welfare.asset.userManagement.service.UserService;
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
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping( "/collection" )
public class InstalmentController {
  private final EmployeeService employeeService;
  private final InstalmentTypeService instalmentTypeService;
  private final InstalmentService instalmentService;
  private final WorkingPlaceService workingPlaceService;
  private final MainAccountService mainAccountService;
  private final UserService userService;
  private final OperatorService operatorService;


  public InstalmentController(EmployeeService employeeService, InstalmentTypeService instalmentTypeService,
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
    WelfarePosition welfarePosition = user.getEmployee().getWelfarePosition();
    if ( welfarePosition != null ) {
      //member and other person can not view employee who need to pay
      if ( welfarePosition.equals(WelfarePosition.OTR) || welfarePosition.equals(WelfarePosition.MBR) ) {
        model.addAttribute("message", "You have no permission to see this");
        return "processManagement/allEmployee";
      }

      List< Employee > employees = employeeService.findAll()
          .stream()
          .filter((x) -> x.getEmployeeStatus().equals(EmployeeStatus.WORKING))
          .collect(Collectors.toList());
      model.addAttribute("employees", employees);

    }
    return "processManagement/allEmployee";
  }

  @GetMapping( "/{id}" )
  public String employeePayment(@PathVariable Integer id, Model model) {
    Employee employee = employeeService.findById(id);
    List< Instalment > paidInstalments = instalmentService.findByEmployee(employee);
    HashSet< String > paidYears = new HashSet<>();
    //set all years on employee must pay
    paidInstalments.forEach((x) -> {
      paidYears.add(x.getInstalmentType().getYear());
    });
    //year and paid amount
    List< YearAndPaidAmount > yearAndPaidAmounts = new ArrayList<>();

    for ( String paidYear : paidYears ) {
      List< BigDecimal > paidAmountForYear = new ArrayList<>();
      List< BigDecimal > paidInstalmentMandatory = new ArrayList<>();
      List< BigDecimal > paidInstalmentOptional = new ArrayList<>();
      for ( Instalment paidInstalment : paidInstalments ) {
        if ( paidInstalment.getInstalmentType().getYear().equals(paidYear) ) {
          paidAmountForYear.add(paidInstalment.getAmount());
          if ( paidInstalment.getInstalmentType().getCollectionType().equals(CollectionType.OPT) ) {
            paidInstalmentOptional.add(paidInstalment.getAmount());
          }
          if ( paidInstalment.getInstalmentType().getCollectionType().equals(CollectionType.MON) ) {
            paidInstalmentMandatory.add(paidInstalment.getAmount());
          }
        }
      }
      List< BigDecimal > needToPayForYears = new ArrayList<>();
      List< BigDecimal > needInstalmentMandatory = new ArrayList<>();
      List< BigDecimal > needInstalmentOptional = new ArrayList<>();

      instalmentTypeService.findByYear(paidYear).forEach(x -> {
        needToPayForYears.add(x.getAmount());
        if ( x.getCollectionType().equals(CollectionType.OPT) ) {
          needInstalmentOptional.add(x.getAmount());
        }
        if ( x.getCollectionType().equals(CollectionType.MON) ) {
          needInstalmentMandatory.add(x.getAmount());
        }
      });

      BigDecimal paidAmount = paidAmountForYear.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
      BigDecimal paidMandatoryAmount = paidInstalmentMandatory.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
      BigDecimal paidOptionalAmount = paidInstalmentOptional.stream().reduce(BigDecimal.ZERO, BigDecimal::add);

      BigDecimal needToPayForYear = needToPayForYears.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
      BigDecimal needToPayForYearMandatory = needInstalmentMandatory.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
      BigDecimal needToPayForYearOptional = needInstalmentOptional.stream().reduce(BigDecimal.ZERO, BigDecimal::add);


      YearAndPaidAmount yearAndPaidAmount = new YearAndPaidAmount();
      yearAndPaidAmount.setYear(paidYear);
      //totalAmount year paidAmount = amount
      yearAndPaidAmount.setPaidAmount(paidAmount);
      yearAndPaidAmount.setYearAmount(needToPayForYear);
      //mandatoryAmount yearMandatory = amount paid
      yearAndPaidAmount.setPaidMandatoryAmount(paidMandatoryAmount);
      yearAndPaidAmount.setYearMandatoryAmount(needToPayForYearMandatory);
      //optionalAmount yearOptional = amount paidOptional
      yearAndPaidAmount.setPaidOptionalAmount(paidOptionalAmount);
      yearAndPaidAmount.setYearOptionalAmount(needToPayForYearOptional);

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

    Employee employee =
        userService.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName()).getEmployee();

    if ( employee.getBoardOfDirectors().equals(BoardOfDirectors.AGT) ) {
      instalment.setInstalmentStatus(InstalmentStatus.AGC);
    }

    if ( employee.getBoardOfDirectors().equals(BoardOfDirectors.TRS) ) {
      instalment.setInstalmentStatus(InstalmentStatus.TA);
    }


    instalmentService.persist(instalment);

    return "redirect:/collection";
  }

  @GetMapping( "/agent" )
  public String collectionAgent(Model model) {
    List< Instalment > instalments = instalmentService.findByInstalmentStatus(InstalmentStatus.AGC);
    model.addAttribute("instalments", instalments);
    List< BigDecimal > collectionAmounts = new ArrayList<>();
    instalments.forEach(x -> collectionAmounts.add(x.getAmount()));
    model.addAttribute("total", collectionAmounts.stream().reduce(BigDecimal.ZERO, BigDecimal::add));
    return "processManagement/allCollection";
  }

  @GetMapping( "/treasure" )
  public String collectionTreasure(Model model) {
    List< Instalment > instalments = instalmentService.findByInstalmentStatus(InstalmentStatus.AGC);

    List< InstalmentApprove > instalmentApproves = new ArrayList<>();

    List< BigDecimal > collectionAmounts = new ArrayList<>();
    instalments.forEach(x -> {
      InstalmentApprove instalmentApprove = new InstalmentApprove();
      instalmentApprove.setInstalment(x);
      instalmentApproves.add(instalmentApprove);
      collectionAmounts.add(x.getAmount());
    });

    InstalmentTreasure instalmentTreasure = new InstalmentTreasure();
    instalmentTreasure.setInstalmentApproves(instalmentApproves);
    instalmentTreasure.setTotal(collectionAmounts.stream().reduce(BigDecimal.ZERO, BigDecimal::add));

    model.addAttribute("instalmentTreasure", instalmentTreasure);

    return "processManagement/allTreasureCollection";
  }

  @PostMapping( "/treasure" )
  public String collectionTreasurePersist(@ModelAttribute InstalmentTreasure instalmentTreasure, Model model) {
    List< BigDecimal > allCollectionAmounts = new ArrayList<>();

    for ( InstalmentApprove instalmentApprove : instalmentTreasure.getInstalmentApproves() ) {
      Instalment instalment = instalmentService.findById(instalmentApprove.getInstalment().getId());
      instalment.setInstalmentStatus(InstalmentStatus.TA);
      allCollectionAmounts.add(instalmentService.persist(instalment).getAmount());
    }

    MainAccount mainAccount = mainAccountService.findByFundType(FundType.INSTALMENTS);
    if ( mainAccount == null ) {
      mainAccount = new MainAccount();
      mainAccount.setAmount(allCollectionAmounts.stream().reduce(BigDecimal.ZERO, BigDecimal::add));
      mainAccount.setFundType(FundType.INSTALMENTS);
    } else {
      mainAccount.setAmount(operatorService.addition(allCollectionAmounts.stream().reduce(BigDecimal.ZERO,
                                                                                          BigDecimal::add),
                                                     mainAccount.getAmount()));
    }
    mainAccountService.persist(mainAccount);

    return "redirect:/collection/treasure";
  }

}