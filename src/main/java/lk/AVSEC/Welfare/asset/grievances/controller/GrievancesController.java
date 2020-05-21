package lk.AVSEC.Welfare.asset.grievances.controller;

import lk.AVSEC.Welfare.asset.grievances.entity.Enum.Priority;
import lk.AVSEC.Welfare.asset.grievances.entity.Grievance;

import lk.AVSEC.Welfare.asset.grievances.service.GrievancesService;
import lk.AVSEC.Welfare.util.interfaces.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/grievances")
public class GrievancesController implements AbstractController<Grievance, Integer> {

    private final GrievancesService grievancesService;

    @Autowired
    public GrievancesController(GrievancesService grievancesService) {
        this.grievancesService = grievancesService;
    }

    private String commonThing(Model model, Boolean booleanValue, Grievance grievanceObject) {

        model.addAttribute("priorities", Priority.values());
        model.addAttribute("addStatus", booleanValue);
        model.addAttribute("grievances", grievanceObject);
        return "grievances/addGrievances";
    }

    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("grievancess", grievancesService.findAll());
        return "grievances/grievances";
    }

    @GetMapping("/add")
    public String form(Model model) {
        return commonThing(model, false, new Grievance());
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable Integer id, Model model) {
        model.addAttribute("grievancesDetail", grievancesService.findById(id));
        return "grievances/grievances-detail";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        return commonThing(model, true, grievancesService.findById(id));
    }

    @PostMapping(value = {"/save", "/update"})
    public String persist(@Valid @ModelAttribute Grievance grievance, BindingResult bindingResult,
                          RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            return commonThing(model, false, grievance);
        }
        redirectAttributes.addFlashAttribute("grievancesDetail", grievancesService.persist(grievance));
        return "redirect:/grievances";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, Model model) {
        grievancesService.delete(id);
        return "redirect:/grievances";
    }

}
