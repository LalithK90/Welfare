package lk.AVSEC.Welfare.asset.district.controller;

import lk.AVSEC.Welfare.asset.commonAsset.model.Enum.Province;
import lk.AVSEC.Welfare.asset.district.entity.District;
import lk.AVSEC.Welfare.asset.district.service.DistrictService;
import lk.AVSEC.Welfare.asset.employee.entity.Employee;
import lk.AVSEC.Welfare.util.interfaces.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/district")
public class DistrictController implements AbstractController<District, Integer> {

    private final DistrictService districtService;

    @Autowired
    public DistrictController(DistrictService districtService) {
        this.districtService = districtService;
    }

    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("districts", districtService.findAll());
        return "district/district";
    }

    @GetMapping("/add")
    public String form(Model model) {
        model.addAttribute("addStatus", false);
        model.addAttribute("provinces", Province.values());
        model.addAttribute("district", new District());
        return "district/addDistrict";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable Integer id, Model model) {
        model.addAttribute("districtDetail", districtService.findById(id));
        return "district/district-detail";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("addStatus", true);
        model.addAttribute("provinces", Province.values());
        model.addAttribute("district", districtService.findById(id));
        return "district/addDistrict";
    }

    @PostMapping(value = {"/save", "/update"})
    public String persist(@Valid @ModelAttribute District district, BindingResult bindingResult,
                          RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("addStatus", false);
            model.addAttribute("provinces", Province.values());
            model.addAttribute("district", district);
            return "district/addDistrict";
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
