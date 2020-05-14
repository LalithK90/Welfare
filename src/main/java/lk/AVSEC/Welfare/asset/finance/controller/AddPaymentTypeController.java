package lk.AVSEC.Welfare.asset.finance.controller;



import lk.AVSEC.Welfare.asset.finance.entity.AddPaymentType;
import lk.AVSEC.Welfare.asset.finance.entity.Enum.CollectionType;
import lk.AVSEC.Welfare.asset.finance.service.AddPaymentTypeService;
import lk.AVSEC.Welfare.util.interfaces.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/addPaymentType")
public class AddPaymentTypeController implements AbstractController<AddPaymentType, Integer> {

    private final AddPaymentTypeService addPaymentTypeService;

    @Autowired
    public AddPaymentTypeController(AddPaymentTypeService addPaymentTypeService) {
        this.addPaymentTypeService = addPaymentTypeService;
    }

    private String commonThing(Model model, Boolean booleanValue, AddPaymentType addPaymentTypeObject) {

        model.addAttribute("collectionType", CollectionType.values());
        model.addAttribute("addStatus", booleanValue);
        model.addAttribute("addPaymentType", addPaymentTypeObject);
        return "finance/addAddPaymentType";
    }

    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("addPaymentTypes", addPaymentTypeService.findAll());
        return "finance/addPaymentType";
    }

    @GetMapping("/add")
    public String form(Model model) {
        return commonThing(model, false, new AddPaymentType());
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable Integer id, Model model) {
        model.addAttribute("addPaymentTypeDetail", addPaymentTypeService.findById(id));
        return "finance/addPaymentType-detail";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        return commonThing(model, true, addPaymentTypeService.findById(id));


    }

    @PostMapping(value = {"/save", "/update"})
    public String persist(@Valid @ModelAttribute AddPaymentType addPaymentType, BindingResult bindingResult,
                          RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            return commonThing(model, false, addPaymentType);
        }
        redirectAttributes.addFlashAttribute("addPaymentTypeDetail", addPaymentTypeService.persist(addPaymentType));
        return "redirect:/addPaymentType";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, Model model) {
        addPaymentTypeService.delete(id);
        return "redirect:/addPaymentType";
    }

}
