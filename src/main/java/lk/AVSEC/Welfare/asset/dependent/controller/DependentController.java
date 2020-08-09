package lk.AVSEC.Welfare.asset.dependent.controller;

import lk.AVSEC.Welfare.asset.dependent.entity.Dependent;
import lk.AVSEC.Welfare.asset.dependent.entity.DependentEmployee;
import lk.AVSEC.Welfare.asset.dependent.entity.Enum.CurrentStatus;
import lk.AVSEC.Welfare.asset.dependent.entity.Enum.Relationship;
import lk.AVSEC.Welfare.asset.dependent.service.DependentEmployeeService;
import lk.AVSEC.Welfare.asset.dependent.service.DependentService;
import lk.AVSEC.Welfare.asset.employee.entity.Employee;
import lk.AVSEC.Welfare.asset.employee.entity.Enum.EmployeeStatus;
import lk.AVSEC.Welfare.asset.employee.service.EmployeeService;
import lk.AVSEC.Welfare.asset.userManagement.service.UserService;
import lk.AVSEC.Welfare.util.interfaces.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.stream.Collectors;

@Controller
@RequestMapping( "/dependent" )
public class DependentController implements AbstractController< Dependent, Integer > {

    private final DependentService dependentService;
    private final DependentEmployeeService dependentEmployeeService;
    private final UserService userService;
    private final EmployeeService employeeService;


    @Autowired
    public DependentController(DependentService dependentService, DependentEmployeeService dependentEmployeeService,
                               UserService userService, EmployeeService employeeService) {
        this.dependentService = dependentService;
        this.dependentEmployeeService = dependentEmployeeService;
        this.userService = userService;
        this.employeeService = employeeService;
    }

    private String commonThing(Model model, Boolean booleanValue, Dependent dependentObject) {
        model.addAttribute("relationship", Relationship.values());
        model.addAttribute("currentStatus", CurrentStatus.values());
        //  model.addAttribute("dependents", Province.values());
        model.addAttribute("addStatus", booleanValue);
        model.addAttribute("dependent", dependentObject);

        return "dependent/addDependent";
    }

    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("dependents", dependentEmployeeService.findAll()
                .stream()
                .distinct()
                .collect(Collectors.toList()));
        return "dependent/dependent";
    }

    @GetMapping( "/add" )
    public String form(Model model) {
        return commonThing(model, false, new Dependent());
    }

    @GetMapping( "/{id}" )
    public String findById(@PathVariable Integer id, Model model) {
        model.addAttribute("dependentDetail", dependentEmployeeService.findById(id));
        return "dependent/dependent-detail";
    }

    @GetMapping( "/edit/{id}" )
    public String edit(@PathVariable Integer id, Model model) {
        Dependent dependent = dependentService.findById(id);
        Employee employee =
                userService.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName()).getEmployee();
        return commonThing(model, true, dependentEmployeeService.findByDependentAndEmployee(dependent, employee));
    }

    @PostMapping( value = {"/save", "/update"} )
    public String persist(@Valid @ModelAttribute Dependent dependent, BindingResult bindingResult,
                          RedirectAttributes redirectAttributes, Model model) {
        Employee employee =
                userService.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName()).getEmployee();

        if ( bindingResult.hasErrors() ) {
            return commonThing(model, false, dependent);
        }

        Dependent saveDependant = new Dependent();
        //if there is registered dependent on system
        if ( !dependent.getNic().isEmpty() ) {
            Dependent dependentDb = dependentService.findByNic(dependent.getNic());
            if ( dependentDb == null ) {
                saveDependant = dependentService.persist(dependent);
            } else {
                saveDependant = dependentDb;
            }
        }

        // create a new dependentEmployee and set value to it
        DependentEmployee dependentEmployee = new DependentEmployee();


        //relationship if wife husband -> employeeOne or employeeTwo
        if ( dependent.getRelationship().equals(Relationship.HUS) || dependent.getRelationship().equals(Relationship.WIF) && !dependent.getEpfNumber().isEmpty() ) {
            Employee employeeDB = employeeService.findByEpf(dependent.getEpfNumber());
            dependentEmployee.setEmployeeTwo(employeeDB);
            dependentEmployee.setRelationship(dependent.getRelationship());
            dependentEmployee.setEmployeeOne(employee);
            dependentEmployee.setRelationship(dependent.getRelationship());
            Dependent dependentAVSEC = new Dependent();
            if ( employeeDB.getEmployeeStatus().equals(EmployeeStatus.RESIGNED) ) {
                dependentAVSEC.setCurrentStatus(CurrentStatus.OTR);
            } else {
                dependentAVSEC.setCurrentStatus(CurrentStatus.ACT);
            }
            dependentAVSEC.setDob(employeeDB.getDateOfBirth());
            dependentAVSEC.setName(employeeDB.getName());
            dependentEmployee.setDependent(dependentAVSEC);
        } else {
            dependentEmployee.setRelationship(dependent.getRelationship());
            dependentEmployee.setEmployeeOne(employee);
            dependentEmployee.setRelationship(dependent.getRelationship());
            dependentEmployee.setDependent(dependent);
        }

        if ( dependent.getRelationship().equals(Relationship.HUS) || dependent.getRelationship().equals(Relationship.WIF) && dependent.getEpfNumber().isEmpty() ) {
            return "redirect:/dependent";
        }


        redirectAttributes.addFlashAttribute("dependentDetail", dependentEmployeeService.persist(dependentEmployee));
        return "redirect:/dependent";
    }

    @GetMapping( "/delete/{id}" )
    public String delete(@PathVariable Integer id, Model model) {
        dependentService.delete(id);
        return "redirect:/dependent";
    }


}
