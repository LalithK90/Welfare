package lk.AVSEC.Welfare.asset.finance.controller;

import lk.AVSEC.Welfare.asset.finance.entity.Enum.CollectionType;
<<<<<<< HEAD
=======
import lk.AVSEC.Welfare.asset.finance.entity.Enum.ExpenseOrReceived;
>>>>>>> 991a9fa77174c9052d9120bd05ad1fd6ca31d6e1
import lk.AVSEC.Welfare.asset.finance.service.InstalmentTypeService;
import lk.AVSEC.Welfare.asset.finance.entity.InstalmentType;
import lk.AVSEC.Welfare.util.interfaces.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
<<<<<<< HEAD
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
=======
import org.springframework.web.bind.annotation.*;
>>>>>>> 991a9fa77174c9052d9120bd05ad1fd6ca31d6e1
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/instalmentType")
public class InstalmentTypeController implements AbstractController<InstalmentType, Integer> {

    private final InstalmentTypeService instalmentTypeService;

    @Autowired
    public InstalmentTypeController(InstalmentTypeService instalmentTypeService) {
        this.instalmentTypeService = instalmentTypeService;
    }

    private String commonThing(Model model, Boolean booleanValue, InstalmentType instalmentType) {
        model.addAttribute("collectionTypes", CollectionType.values());
<<<<<<< HEAD
=======
        model.addAttribute("expenseOrReceiveds", ExpenseOrReceived.values());
>>>>>>> 991a9fa77174c9052d9120bd05ad1fd6ca31d6e1
        model.addAttribute("addStatus", booleanValue);
        model.addAttribute("instalmentType", instalmentType);
        return "instalmentType/addInstalmentType";
    }

<<<<<<< HEAD
    @Override
=======
    @GetMapping
>>>>>>> 991a9fa77174c9052d9120bd05ad1fd6ca31d6e1
    public String findAll(Model model) {
        model.addAttribute("instalmentTypeis", instalmentTypeService.findAll());
        return "instalmentType/instalmentType";
    }

<<<<<<< HEAD
    @Override
=======
    @GetMapping("/add")
>>>>>>> 991a9fa77174c9052d9120bd05ad1fd6ca31d6e1
    public String form(Model model) {
        return commonThing(model, false, new InstalmentType());
    }

<<<<<<< HEAD
    @Override
    public String findById(Integer id, Model model) {
=======
    @GetMapping("/view/{id}")
    public String findById(@PathVariable Integer id, Model model) {
>>>>>>> 991a9fa77174c9052d9120bd05ad1fd6ca31d6e1
        model.addAttribute("instalmentTypeDetail", instalmentTypeService.findById(id));
        return "instalmentType/instalmentType-detail";
    }

<<<<<<< HEAD
    @Override
    public String edit(Integer id, Model model) {
        return commonThing(model, true, instalmentTypeService.findById(id));
    }

    @Override
=======
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        return commonThing(model, true, instalmentTypeService.findById(id));
    }

    @PostMapping(value = {"/save","/update"})
>>>>>>> 991a9fa77174c9052d9120bd05ad1fd6ca31d6e1
    public String persist(@Valid @ModelAttribute InstalmentType instalmentType, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) throws Exception {
        if (bindingResult.hasErrors()) {
            return commonThing(model, false, instalmentType);
        }
        redirectAttributes.addFlashAttribute("instalmentTypeDetail", instalmentTypeService.persist(instalmentType));
        return "redirect:/instalmentType";
    }

<<<<<<< HEAD
    @Override
    public String delete(Integer id, Model model) {
=======
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, Model model) {
>>>>>>> 991a9fa77174c9052d9120bd05ad1fd6ca31d6e1
        // there is no permission to delete
        return "redirect:/instalmentType";
    }
}
