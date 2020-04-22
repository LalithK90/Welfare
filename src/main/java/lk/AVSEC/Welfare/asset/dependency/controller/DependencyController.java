package lk.AVSEC.Welfare.asset.dependency.controller;

import lk.AVSEC.Welfare.asset.commonAsset.model.Enum.Province;
import lk.AVSEC.Welfare.asset.dependency.entity.Dependency;
import lk.AVSEC.Welfare.asset.dependency.entity.Enum.Relationship;
import lk.AVSEC.Welfare.asset.dependency.service.DependencyService;
import lk.AVSEC.Welfare.asset.grievances.entity.Enum.Priority;
import lk.AVSEC.Welfare.util.interfaces.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/dependency")
public class DependencyController implements AbstractController<Dependency, Integer> {

    private final DependencyService dependencyService;

    @Autowired
    public DependencyController(DependencyService dependencyService) {
        this.dependencyService = dependencyService;
    }

    private String commonThing(Model model, Boolean booleanValue, Dependency dependencyObject) {
       model.addAttribute("relationship", Relationship.values());

      //  model.addAttribute("dependencys", Province.values());
        model.addAttribute("addStatus", booleanValue);
        model.addAttribute("dependency", dependencyObject);
        return "dependency/addDependency";
    }

    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("dependencys", dependencyService.findAll());
        return "dependency/dependency";
    }

    @GetMapping("/add")
    public String form(Model model) {
        return commonThing(model, false, new Dependency());
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable Integer id, Model model) {
        model.addAttribute("dependencyDetail", dependencyService.findById(id));
        return "dependency/dependency-detail";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        return commonThing(model, true, dependencyService.findById(id));
    }

    @PostMapping(value = {"/save", "/update"})
    public String persist(@Valid @ModelAttribute Dependency dependency, BindingResult bindingResult,
                          RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            return commonThing(model, false, dependency);
        }
        redirectAttributes.addFlashAttribute("dependencyDetail", dependencyService.persist(dependency));
        return "redirect:/dependency";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, Model model) {
        dependencyService.delete(id);
        return "redirect:/dependency";
    }


}
