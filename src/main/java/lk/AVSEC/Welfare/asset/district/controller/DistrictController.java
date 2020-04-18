package lk.AVSEC.Welfare.asset.district.controller;

import lk.AVSEC.Welfare.asset.commonAsset.model.Enum.Province;
import lk.AVSEC.Welfare.asset.district.entity.District;
import lk.AVSEC.Welfare.asset.district.service.DistrictService;
import lk.AVSEC.Welfare.util.interfaces.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/district")
public class DistrictController implements AbstractController<District, Integer> {

    private final DistrictService districtService;

    @Autowired
    public DistrictController(DistrictService districtService) {
        this.districtService = districtService;
    }

    private String commonThing(Model model, Boolean booleanValue, District districtObject) {
        model.addAttribute("provinces", Province.values());
        model.addAttribute("addStatus", booleanValue);
        model.addAttribute("district", districtObject);
        return "district/addDistrict";
    }

    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("districts", districtService.findAll());
        return "district/district";
    }

    @GetMapping("/add")
    public String form(Model model) {
        return commonThing(model, false, new District());
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable Integer id, Model model) {
        model.addAttribute("districtDetail", districtService.findById(id));
        return "district/district-detail";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        return commonThing(model,true, districtService.findById(id));
    }

    @PostMapping(value = {"/save", "/update"})
    public String persist(@Valid @ModelAttribute District district, BindingResult bindingResult,
                          RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            return commonThing(model,false, district);
        }
        redirectAttributes.addFlashAttribute("districtDetail", districtService.persist(district));
        return "redirect:/district";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, Model model) {
        districtService.delete(id);
        return "redirect:/district";
    }
}
