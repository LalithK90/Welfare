package lk.AVSEC.Welfare.asset.qualification.controller;

import lk.AVSEC.Welfare.asset.commonAsset.model.Enum.Province;
import lk.AVSEC.Welfare.asset.employee.entity.Qualification;
import lk.AVSEC.Welfare.asset.employee.service.QualificationService;
import lk.AVSEC.Welfare.util.interfaces.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/qualification")
public class QualificarionController implements AbstractController<Qualification, Integer> {

    private final QualificationService qualificationService;

    @Autowired
    public QualificarionController(QualificationService qualificationService) {
        this.qualificationService = qualificationService;
    }

    private String commonThing(Model model, Boolean booleanValue, Qualification qualificationObject) {
        model.addAttribute("provinces", Province.values());
        model.addAttribute("addStatus", booleanValue);
        model.addAttribute("qualification", qualificationObject);
        return "qualification/addQualification";
    }

    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("qualifications", qualificationService.findAll());
        return "qualification/qualification";
    }

    @GetMapping("/add")
    public String form(Model model) {
        return commonThing(model, false, new Qualification());
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable Integer id, Model model) {
        model.addAttribute("qualificationDetail", qualificationService.findById(id));
        return "qualification/qualification-detail";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        return commonThing(model, true, qualificationService.findById(id));
    }

    @PostMapping(value = {"/save", "/update"})
    public String persist(@Valid @ModelAttribute Qualification qualification, BindingResult bindingResult,
                          RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            return commonThing(model, false, qualification);
        }
        redirectAttributes.addFlashAttribute("qualificationDetail", qualificationService.persist(qualification));
        return "redirect:/qualification";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, Model model) {
        qualificationService.delete(id);
        return "redirect:/qualification";
    }


}
