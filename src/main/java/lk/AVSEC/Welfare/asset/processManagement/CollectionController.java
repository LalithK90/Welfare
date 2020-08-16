package lk.AVSEC.Welfare.asset.processManagement;

import lk.AVSEC.Welfare.asset.employee.entity.Employee;
import lk.AVSEC.Welfare.asset.employee.entity.Enum.WelfarePosition;
import lk.AVSEC.Welfare.asset.employee.service.EmployeeService;
import lk.AVSEC.Welfare.asset.finance.entity.Instalment;
import lk.AVSEC.Welfare.asset.finance.service.InstalmentService;
import lk.AVSEC.Welfare.asset.finance.service.InstalmentTypeService;
import lk.AVSEC.Welfare.asset.finance.service.MainAccountService;
import lk.AVSEC.Welfare.asset.userManagement.entity.User;
import lk.AVSEC.Welfare.asset.employee.service.EmployeeFilesService;
import lk.AVSEC.Welfare.asset.userManagement.service.UserService;
import lk.AVSEC.Welfare.asset.workingPlace.entity.WorkingPlace;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping( "/collection" )
public class CollectionController {
    private final EmployeeService employeeService;
    private final InstalmentTypeService instalmentTypeService;
    private final InstalmentService instalmentService;
    private final MainAccountService mainAccountService;
    private final UserService userService;
    private final EmployeeFileService employeeFileService;


    public CollectionController(EmployeeService employeeService, InstalmentTypeService instalmentTypeService,
                                InstalmentService instalmentService, MainAccountService mainAccountService,
                                UserService userService, EmployeeFileService employeeFileService) {
        this.employeeService = employeeService;
        this.instalmentTypeService = instalmentTypeService;
        this.instalmentService = instalmentService;
        this.mainAccountService = mainAccountService;
        this.userService = userService;
        this.employeeFileService = employeeFileService;
    }

    @GetMapping
    public String allEmployee(Model model) {
        /*User user =
                userService.findById(userService.findByUserIdByUserName(SecurityContextHolder.getContext().getAuthentication().getName()));
        WorkingPlace workingPlace = user.getEmployee().getWorkingPlace();
        WelfarePosition welfarePosition = user.getEmployee().getWelfarePosition();
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
        }*/
        model.addAttribute("employees", employeeService.findAll());
        return "processManagement/allEmployee";
    }

    @GetMapping( "/{id}" )
    public String employeePayment(@PathVariable Integer id, Model model) {
        Employee employee = employeeService.findById(id);
        //todo need to find payment history and available balance need show
        model.addAttribute("employeeDetail", employee);
        model.addAttribute("files", employeeFilesService.employeeFileDownloadLinks(employee));
        model.addAttribute("instalment", new Instalment());
        model.addAttribute("instalments", instalmentService.findByEmployee(employee));
        model.addAttribute("instalmentTypes", instalmentTypeService.findAll());

        return "processManagement/addInstalment";
    }

}
