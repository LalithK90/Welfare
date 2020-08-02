package lk.AVSEC.Welfare.asset.dependent.controller;

import lk.AVSEC.Welfare.asset.dependent.entity.Dependent;
<<<<<<< HEAD
import lk.AVSEC.Welfare.asset.dependent.entity.Enum.CurrentStatus;
import lk.AVSEC.Welfare.asset.dependent.entity.Enum.Relationship;
import lk.AVSEC.Welfare.asset.dependent.service.DependentService;
import lk.AVSEC.Welfare.util.interfaces.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
=======
import lk.AVSEC.Welfare.asset.dependent.entity.DependentEmployee;
import lk.AVSEC.Welfare.asset.dependent.entity.Enum.CurrentStatus;
import lk.AVSEC.Welfare.asset.dependent.entity.Enum.Relationship;
import lk.AVSEC.Welfare.asset.dependent.service.DependentEmployeeService;
import lk.AVSEC.Welfare.asset.dependent.service.DependentService;
import lk.AVSEC.Welfare.asset.employee.entity.Employee;
import lk.AVSEC.Welfare.asset.employee.service.EmployeeService;
import lk.AVSEC.Welfare.asset.userManagement.service.UserService;
import lk.AVSEC.Welfare.util.interfaces.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
>>>>>>> 991a9fa77174c9052d9120bd05ad1fd6ca31d6e1
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
<<<<<<< HEAD
=======
import java.util.stream.Collectors;
>>>>>>> 991a9fa77174c9052d9120bd05ad1fd6ca31d6e1

@Controller
@RequestMapping("/dependent")
public class DependentController implements AbstractController<Dependent, Integer> {
<<<<<<< HEAD

    private final DependentService dependentService;

    @Autowired
    public DependentController(DependentService dependentService) {
        this.dependentService = dependentService;
    }

    private String commonThing(Model model, Boolean booleanValue, Dependent dependentObject) {
       model.addAttribute("relationship", Relationship.values());
        model.addAttribute("currentStatus", CurrentStatus.values());
      //  model.addAttribute("dependents", Province.values());
=======
    //todo -> need to dependentEmployee view and edit
    private final DependentService dependentService;
    private final DependentEmployeeService dependentEmployeeService;
    private final UserService userService;
    private final EmployeeService employeeService;


    @Autowired
    public DependentController(DependentService dependentService, DependentEmployeeService dependentEmployeeService, UserService userService, EmployeeService employeeService) {
        this.dependentService = dependentService;
        this.dependentEmployeeService = dependentEmployeeService;
        this.userService = userService;
        this.employeeService = employeeService;
    }

    private String commonThing(Model model, Boolean booleanValue, Dependent dependentObject) {
        model.addAttribute("relationship", Relationship.values());
        model.addAttribute("currentStatus", CurrentStatus.values());
        //  model.addAttribute("dependents", Province.values());
>>>>>>> 991a9fa77174c9052d9120bd05ad1fd6ca31d6e1
        model.addAttribute("addStatus", booleanValue);
        model.addAttribute("dependent", dependentObject);

        return "dependent/addDependent";
    }

    @GetMapping
    public String findAll(Model model) {
<<<<<<< HEAD
        model.addAttribute("dependents", dependentService.findAll());
=======
        model.addAttribute("dependents", dependentEmployeeService.findAll()
                .stream()
                .distinct()
                .collect(Collectors.toList()));
>>>>>>> 991a9fa77174c9052d9120bd05ad1fd6ca31d6e1
        return "dependent/dependent";
    }

    @GetMapping("/add")
    public String form(Model model) {
        return commonThing(model, false, new Dependent());
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable Integer id, Model model) {
<<<<<<< HEAD
        model.addAttribute("dependentDetail", dependentService.findById(id));
=======
        model.addAttribute("dependentDetail", dependentEmployeeService.findById(id));
>>>>>>> 991a9fa77174c9052d9120bd05ad1fd6ca31d6e1
        return "dependent/dependent-detail";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
<<<<<<< HEAD
        return commonThing(model, true, dependentService.findById(id));
=======
        Dependent dependent = dependentService.findById(id);
        Employee employee = userService.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName()).getEmployee();
        return commonThing(model, true, dependentEmployeeService.findByDependentAndEmployee(dependent, employee));
>>>>>>> 991a9fa77174c9052d9120bd05ad1fd6ca31d6e1
    }

    @PostMapping(value = {"/save", "/update"})
    public String persist(@Valid @ModelAttribute Dependent dependent, BindingResult bindingResult,
                          RedirectAttributes redirectAttributes, Model model) {
<<<<<<< HEAD
        if (bindingResult.hasErrors()) {
            return commonThing(model, false, dependent);
        }
        redirectAttributes.addFlashAttribute("dependentDetail", dependentService.persist(dependent));
=======
        Employee employee = userService.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName()).getEmployee();

        if (bindingResult.hasErrors()) {
            return commonThing(model, false, dependent);
        }

        Dependent saveDependant = new Dependent();
        //if there is registered dependent on system
        if (!dependent.getNic().isEmpty()) {
            Dependent dependentDb = dependentService.findByNic(dependent.getNic());
            if (dependentDb == null) {
                saveDependant = dependentService.persist(dependent);
            } else {
                saveDependant = dependentDb;
            }
        }

        // create a new dependentEmployee and set value to it
        DependentEmployee dependentEmployee = new DependentEmployee();


        //relationship if wife husband -> employeeOne or employeeTwo
        if (dependent.getRelationship().equals(Relationship.HUS) || dependent.getRelationship().equals(Relationship.WIF) && !dependent.getEpfNumber().isEmpty()) {
            dependentEmployee.setEmployeeTwo(employeeService.findByEpf(dependent.getEpfNumber()));
        }
        if (dependent.getRelationship().equals(Relationship.HUS) || dependent.getRelationship().equals(Relationship.WIF) && dependent.getEpfNumber().isEmpty()) {
            return "redirect:/dependent";
        }

        dependentEmployee.setRelationship(dependent.getRelationship());
        dependentEmployee.setEmployeeOne(employee);
        dependentEmployee.setRelationship(dependent.getRelationship());
        dependentEmployee.setDependent(dependent);

        redirectAttributes.addFlashAttribute("dependentDetail", dependentEmployeeService.persist(dependentEmployee));
>>>>>>> 991a9fa77174c9052d9120bd05ad1fd6ca31d6e1
        return "redirect:/dependent";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, Model model) {
        dependentService.delete(id);
        return "redirect:/dependent";
    }


}
