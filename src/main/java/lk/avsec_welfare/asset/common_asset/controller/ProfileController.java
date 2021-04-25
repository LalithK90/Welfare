package lk.avsec_welfare.asset.common_asset.controller;

import lk.avsec_welfare.asset.dependent.service.DependentEmployeeService;
import lk.avsec_welfare.asset.employee.entity.Employee;
import lk.avsec_welfare.asset.employee.service.EmployeeFilesService;
import lk.avsec_welfare.asset.employee_working_place.service.EmployeeWorkingPlaceService;
import lk.avsec_welfare.asset.userManagement.entity.PasswordChange;
import lk.avsec_welfare.asset.userManagement.entity.User;
import lk.avsec_welfare.asset.userManagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class ProfileController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final EmployeeFilesService employeeFilesService;
    private final EmployeeWorkingPlaceService employeeWorkingPlaceService;
    private final DependentEmployeeService dependentEmployeeService;

    @Autowired
    public ProfileController(UserService userService, PasswordEncoder passwordEncoder,
                             EmployeeFilesService employeeFilesService, EmployeeWorkingPlaceService employeeWorkingPlaceService, DependentEmployeeService dependentEmployeeService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.employeeFilesService = employeeFilesService;
        this.employeeWorkingPlaceService = employeeWorkingPlaceService;
        this.dependentEmployeeService = dependentEmployeeService;
    }

    @GetMapping( value = "/profile" )
    public String userProfile(Model model, Principal principal) {
        Employee employee = userService.findByUserName(principal.getName()).getEmployee();
        model.addAttribute("addStatus", true);
        model.addAttribute("employeeDetail", employee);
        model.addAttribute("files", employeeFilesService.employeeFileDownloadLinks(employee));
        model.addAttribute("employeeWorkingPlaces", employeeWorkingPlaceService.findByEmployee(employee));
        model.addAttribute("dependentEmployees", dependentEmployeeService.findByEmployee(employee));
        model.addAttribute("contendHeader", "Employee View Details");

        return "employee/employee-detail";
    }

    @GetMapping( value = "/passwordChange" )
    public String passwordChangeForm(Model model) {
        model.addAttribute("pswChange", new PasswordChange());
        model.addAttribute("contendHeader", "Password Change");
        return "login/passwordChange";
    }

    @PostMapping( value = "/passwordChange" )
    public String passwordChange(@Valid @ModelAttribute PasswordChange passwordChange,
                                 BindingResult result, RedirectAttributes redirectAttributes) {
        User user =
                userService.findById(userService.findByUserIdByUserName(SecurityContextHolder.getContext().getAuthentication().getName()));

        if ( passwordEncoder.matches(passwordChange.getOldPassword(), user.getPassword()) && !result.hasErrors() && passwordChange.getNewPassword().equals(passwordChange.getNewPasswordConform()) ) {

            user.setPassword(passwordChange.getNewPassword());
            userService.persist(user);

            redirectAttributes.addFlashAttribute("message", "Congratulations .!! Success password is changed");
            redirectAttributes.addFlashAttribute("alertClass", "alert-success");

            return "redirect:/home";

        }
        redirectAttributes.addFlashAttribute("message", "Failed");
        redirectAttributes.addFlashAttribute("alertClass", "alert-danger");

        return "redirect:/passwordChange";

    }
}
