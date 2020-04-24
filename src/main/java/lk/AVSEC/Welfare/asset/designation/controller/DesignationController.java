package lk.AVSEC.Welfare.asset.designation.controller;

import lk.AVSEC.Welfare.asset.designation.entity.Designation;
import lk.AVSEC.Welfare.asset.designation.entity.Enum.SalaryScale;
import lk.AVSEC.Welfare.asset.designation.entity.Enum.CategoryType;
import lk.AVSEC.Welfare.asset.designation.service.DesignationService;
import lk.AVSEC.Welfare.util.interfaces.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/designation")
public class DesignationController implements AbstractController<Designation, Integer> {

    private final DesignationService designationService;

    @Autowired
    public DesignationController(DesignationService designationService) {
        this.designationService = designationService;
    }

    private String commonThing(Model model, Boolean booleanValue, Designation designationObject) {

        model.addAttribute("salaryScales", SalaryScale.values());
        model.addAttribute("categoryTypes", CategoryType.values());
        model.addAttribute("addStatus", booleanValue);
        model.addAttribute("designation", designationObject);
        return "designation/addDesignation";
    }

    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("designations", designationService.findAll());
        return "designation/designation";
    }

    @GetMapping("/add")
    public String form(Model model) {
        return commonThing(model, false, new Designation());
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable Integer id, Model model) {
        model.addAttribute("designationDetail", designationService.findById(id));
        return "designation/designation-detail";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        return commonThing(model, true, designationService.findById(id));
    }

    @PostMapping(value = {"/save", "/update"})
    public String persist(@Valid @ModelAttribute Designation designation, BindingResult bindingResult,
                          RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            return commonThing(model, false, designation);
        }
        redirectAttributes.addFlashAttribute("designationDetail", designationService.persist(designation));
        return "redirect:/designation";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, Model model) {
        designationService.delete(id);
        return "redirect:/designation";
    }


}
