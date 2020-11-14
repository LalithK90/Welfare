package lk.AVSEC.Welfare.asset.processManagement;

import lk.AVSEC.Welfare.asset.employee.entity.Employee;
import lk.AVSEC.Welfare.asset.employee.entity.Enum.WelfarePosition;
import lk.AVSEC.Welfare.asset.employee.service.EmployeeService;
import lk.AVSEC.Welfare.asset.finance.entity.Instalment;
import lk.AVSEC.Welfare.asset.finance.service.InstalmentService;
import lk.AVSEC.Welfare.asset.finance.service.InstalmentTypeService;
import lk.AVSEC.Welfare.asset.finance.service.MainAccountService;
import lk.AVSEC.Welfare.asset.processManagement.commonModel.YearAndPaidAmount;
import lk.AVSEC.Welfare.asset.userManagement.entity.User;
import lk.AVSEC.Welfare.asset.userManagement.service.UserService;
import lk.AVSEC.Welfare.asset.workingPlace.entity.WorkingPlace;
import lk.AVSEC.Welfare.util.service.OperatorService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/collection")
public class CollectionController {
    private final EmployeeService employeeService;
    private final InstalmentTypeService instalmentTypeService;
    private final InstalmentService instalmentService;
    private final MainAccountService mainAccountService;
    private final UserService userService;
    private final OperatorService operatorService;


    public CollectionController(EmployeeService employeeService, InstalmentTypeService instalmentTypeService,
                                InstalmentService instalmentService, MainAccountService mainAccountService,
                                UserService userService, OperatorService operatorService) {
        this.employeeService = employeeService;
        this.instalmentTypeService = instalmentTypeService;
        this.instalmentService = instalmentService;
        this.mainAccountService = mainAccountService;
        this.userService = userService;
        this.operatorService = operatorService;
    }

    @GetMapping
    public String allEmployee(Model model) {
        User user =
                userService.findById(userService.findByUserIdByUserName(SecurityContextHolder.getContext().getAuthentication().getName()));
        WorkingPlace workingPlace = user.getEmployee().getWorkingPlace();
        WelfarePosition welfarePosition = user.getEmployee().getWelfarePosition();
        if (welfarePosition != null) {
            //member and other person can not view employee who need to pay
            if (welfarePosition.equals(WelfarePosition.OTR) || welfarePosition.equals(WelfarePosition.MBR)) {
                model.addAttribute("message", "You have no permission to see this");
            }
            if (welfarePosition.equals(WelfarePosition.AGT)) {
                List<Employee> employees = employeeService.findAll()
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

    @GetMapping("/{id}")
    public String employeePayment(@PathVariable Integer id, Model model) {
        Employee employee = employeeService.findById(id);
        //todo need to find payment history and available balance need show
        List<Instalment> paidInstalments = instalmentService.findByEmployee(employee);
        List<String> paidYears = new ArrayList<>();
        //set all years on employee must pay
        paidInstalments.forEach((x) -> {
            paidYears.add(x.getInstalmentType().getYear());
        });
        //remove duplicate years on paidYears array
        paidYears.stream().distinct().collect(Collectors.toList());
        //year and paid amount
        List<YearAndPaidAmount> yearAndPaidAmounts = new ArrayList<>();
        //Map<Year,BigDecimal>
        //need to create year and total payment for particular year
        for (String paidYear : paidYears) {
            BigDecimal paidAmountForYear = BigDecimal.ZERO;
            BigDecimal yearAmount = BigDecimal.ZERO;
            for (Instalment paidInstalment : paidInstalments) {
                if (paidInstalment.getInstalmentType().getYear().equals(paidYear)) {
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
        model.addAttribute("yearAndPaidAmount", yearAndPaidAmounts);
        model.addAttribute("employeeDetail", employee);
        model.addAttribute("instalment", new Instalment());
        model.addAttribute("instalments", instalmentService.findByEmployee(employee));
        model.addAttribute("instalmentTypes", instalmentTypeService.findAll());
        return "processManagement/addInstalment";
    }

    @PostMapping
    public String saveInstalment(@Valid @ModelAttribute Instalment instalment, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "redirect:/collection/".concat(String.valueOf(instalment.getEmployee().getId()));
        }
        instalmentService.persist(instalment);
        return "redirect:/collection";
    }
}
