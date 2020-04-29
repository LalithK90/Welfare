package lk.AVSEC.Welfare.asset.finance.controller;

import lk.AVSEC.Welfare.asset.finance.entity.Enum.CollectionType;
import lk.AVSEC.Welfare.asset.finance.entity.Enum.Status;
import lk.AVSEC.Welfare.asset.finance.entity.AdditionalPayment;
import lk.AVSEC.Welfare.asset.finance.service.AdditionalPaymentService;
import lk.AVSEC.Welfare.util.interfaces.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/additionalPayment")
public class AdditionalPaymentController implements AbstractController<AdditionalPayment, Integer> {

    private final AdditionalPaymentService additionalPaymentService;

    @Autowired
    public AdditionalPaymentController(AdditionalPaymentService additionalPaymentService) {
        this.additionalPaymentService = additionalPaymentService;
    }

    private String commonThing(Model model, Boolean booleanValue, AdditionalPayment additionalPaymentObject) {

        model.addAttribute("collectionType", CollectionType.values());
        model.addAttribute("addStatus", booleanValue);
        model.addAttribute("additionalPayment", additionalPaymentObject);
        return "finance/addAdditionalPayment";
    }

    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("additionalPayments", additionalPaymentService.findAll());
        return "finance/additionalPayment";
    }

    @GetMapping("/add")
    public String form(Model model) {
        return commonThing(model, false, new AdditionalPayment());
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable Integer id, Model model) {
        model.addAttribute("additionalPaymentDetail", additionalPaymentService.findById(id));
        return "finance/additionalPayment-detail";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        return commonThing(model, true, additionalPaymentService.findById(id));


    }

    @PostMapping(value = {"/save", "/update"})
    public String persist(@Valid @ModelAttribute AdditionalPayment additionalPayment, BindingResult bindingResult,
                          RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            return commonThing(model, false, additionalPayment);
        }
        redirectAttributes.addFlashAttribute("additionalPaymentDetail", additionalPaymentService.persist(additionalPayment));
        return "redirect:/additionalPayment";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, Model model) {
        additionalPaymentService.delete(id);
        return "redirect:/additionalPayment";
    }

}
