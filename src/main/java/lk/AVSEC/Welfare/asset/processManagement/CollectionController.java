package lk.AVSEC.Welfare.asset.processManagement;

import lk.AVSEC.Welfare.asset.employee.entity.Employee;
import lk.AVSEC.Welfare.asset.employee.service.EmployeeService;
import lk.AVSEC.Welfare.asset.finance.service.InstalmentService;
import lk.AVSEC.Welfare.asset.finance.service.InstalmentTypeService;
import lk.AVSEC.Welfare.asset.finance.service.MainAccountService;
import lk.AVSEC.Welfare.asset.userManagement.entity.User;
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


    public CollectionController(EmployeeService employeeService, InstalmentTypeService instalmentTypeService,
                                InstalmentService instalmentService, MainAccountService mainAccountService,
                                UserService userService) {
        this.employeeService = employeeService;
        this.instalmentTypeService = instalmentTypeService;
        this.instalmentService = instalmentService;
        this.mainAccountService = mainAccountService;
        this.userService = userService;
    }

    @GetMapping
    public String allEmployee(Model model) {
      //  User user =                userService.findById(userService.findByUserIdByUserName(SecurityContextHolder.getContext().getAuthentication().getName()));
 /*       for ( Role role : user.getRoles() ) {
            if ( role. )
        }*/
      //  WorkingPlace workingPlace = user.getEmployee().getWorkingPlace();

/*        List< Employee > employees = employeeService.findAll()
                .stream()
                .filter((x) -> x.getWorkingPlace().equals(workingPlace))
                .collect(Collectors.toList());*/
        model.addAttribute("employees", employeeService.findAll());

        return "processManagement/allEmployee";
    }

    @GetMapping( "/{id}" )
    public String employeePayment(@PathVariable Integer id, Model model) {
        System.out.println(id);
        return "processManagement/allEmployee";
    }

}
