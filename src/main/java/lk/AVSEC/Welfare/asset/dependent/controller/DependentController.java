package lk.AVSEC.Welfare.asset.dependent.controller;

import lk.AVSEC.Welfare.asset.dependent.entity.Dependent;
import lk.AVSEC.Welfare.asset.dependent.entity.Enum.CurrentStatus;
import lk.AVSEC.Welfare.asset.dependent.entity.Enum.Relationship;
import lk.AVSEC.Welfare.asset.dependent.service.DependentService;
import lk.AVSEC.Welfare.util.interfaces.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/dependent")
public class DependentController implements AbstractController<Dependent, Integer> {

    private final DependentService dependentService;

    @Autowired
    public DependentController(DependentService dependentService) {
        this.dependentService = dependentService;
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
        model.addAttribute("dependents", dependentService.findAll());
        return "dependent/dependent";
    }

    @GetMapping("/add")
    public String form(Model model) {
        return commonThing(model, false, new Dependent());
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable Integer id, Model model) {
        model.addAttribute("dependentDetail", dependentService.findById(id));
        return "dependent/dependent-detail";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        return commonThing(model, true, dependentService.findById(id));
    }

    @PostMapping(value = {"/save", "/update"})
    public String persist(@Valid @ModelAttribute Dependent dependent, BindingResult bindingResult,
                          RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            return commonThing(model, false, dependent);
        }
        redirectAttributes.addFlashAttribute("dependentDetail", dependentService.persist(dependent));
        return "redirect:/dependent";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, Model model) {
        dependentService.delete(id);
        return "redirect:/dependent";
    }


}
