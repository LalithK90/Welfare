package lk.AVSEC.Welfare.asset.district.controller;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import lk.AVSEC.Welfare.asset.commonAsset.model.Enum.Province;
import lk.AVSEC.Welfare.asset.district.entity.District;
import lk.AVSEC.Welfare.asset.district.service.DistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DistrictRestController {
    private final DistrictService districtService;

    @Autowired
    public DistrictRestController(DistrictService districtService) {
        this.districtService = districtService;
    }

    @GetMapping(value = "/getDistrict/{province}")
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
