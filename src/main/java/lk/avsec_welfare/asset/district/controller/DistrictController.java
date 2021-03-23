package lk.avsec_welfare.asset.district.controller;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import lk.avsec_welfare.asset.common_asset.model.enums.Province;
import lk.avsec_welfare.asset.district.entity.District;
import lk.avsec_welfare.asset.district.service.DistrictService;
import lk.avsec_welfare.util.interfaces.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/district")
public class DistrictController implements AbstractController< District, Integer> {

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
        return commonThing(model, true, districtService.findById(id));
    }

    @PostMapping(value = {"/save", "/update"})
    public String persist(@Valid @ModelAttribute District district, BindingResult bindingResult,
                          RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            return commonThing(model, false, district);
        }
        redirectAttributes.addFlashAttribute("districtDetail", districtService.persist(district));
        return "redirect:/district";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, Model model) {
        districtService.delete(id);
        return "redirect:/district";
    }
    @GetMapping(value = "/getDistrict/{province}")
    @ResponseBody
    public MappingJacksonValue getDistrictByProvince(@PathVariable String province) {
       /* District district = new District();
        district.setProvince(Province.valueOf(province));
*//*{
    "name":"Lositha",
            "age":26
        }*/
        //MappingJacksonValue
        List<District> districts = districtService.findByProvince(Province.valueOf(province));
        //employeeService.findByWorkingPlace(workingPlaceService.findById(id));

        //Create new mapping jackson value and set it to which was need to filter
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(districts);

        //simpleBeanPropertyFilter :-  need to give any id to addFilter method and created filter which was mentioned
        // what parameter's necessary to provide
        SimpleBeanPropertyFilter simpleBeanPropertyFilter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id", "name");
        //filters :-  set front end required value to before filter

        FilterProvider filters = new SimpleFilterProvider()
                .addFilter("District", simpleBeanPropertyFilter);
        //Employee :- need to annotate relevant class with JsonFilter  {@JsonFilter("Employee") }
        mappingJacksonValue.setFilters(filters);

        return mappingJacksonValue;
    }

}
