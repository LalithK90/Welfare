package lk.AVSEC.Welfare.asset.finance.controller;

import lk.AVSEC.Welfare.asset.finance.entity.Enum.CollectionType;
import lk.AVSEC.Welfare.asset.finance.entity.Enum.Status;
import lk.AVSEC.Welfare.asset.finance.entity.FinancialYear;
import lk.AVSEC.Welfare.asset.finance.service.FinancialYearService;
import lk.AVSEC.Welfare.util.interfaces.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/financialYear")
public class FinancialYearController implements AbstractController<FinancialYear, Integer> {

    private final FinancialYearService financialYearService;

    @Autowired
    public FinancialYearController(FinancialYearService financialYearService) {
        this.financialYearService = financialYearService;
    }

    private String commonThing(Model model, Boolean booleanValue, FinancialYear financialYearObject) {

        model.addAttribute("status", Status.values());
        model.addAttribute("addStatus", booleanValue);
        model.addAttribute("financialYear", financialYearObject);
        return "finance/addFinancialYear";
    }

    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("financialYears", financialYearService.findAll());
        return "finance/financialYear";
    }

    @GetMapping("/add")
    public String form(Model model) {
        return commonThing(model, false, new FinancialYear());
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable Integer id, Model model) {
        model.addAttribute("financialYearDetail", financialYearService.findById(id));
        return "finance/financialYear-detail";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        return commonThing(model, true, financialYearService.findById(id));


    }

    @PostMapping(value = {"/save", "/update"})
    public String persist(@Valid @ModelAttribute FinancialYear financialYear, BindingResult bindingResult,
                          RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            return commonThing(model, false, financialYear);
        }
        redirectAttributes.addFlashAttribute("financialYearDetail", financialYearService.persist(financialYear));
        return "redirect:/financialYear";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, Model model) {
        financialYearService.delete(id);
        return "redirect:/financialYear";
    }

}
