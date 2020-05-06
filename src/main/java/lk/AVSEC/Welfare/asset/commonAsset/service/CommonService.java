package lk.AVSEC.Welfare.asset.commonAsset.service;

import lk.AVSEC.Welfare.asset.supplier.entity.Supplier;
import lk.AVSEC.Welfare.asset.supplier.service.SupplierService;
import lk.AVSEC.Welfare.asset.commonAsset.model.Enum.BloodGroup;
import lk.AVSEC.Welfare.asset.commonAsset.model.Enum.CivilStatus;
import lk.AVSEC.Welfare.asset.commonAsset.model.Enum.Gender;
import lk.AVSEC.Welfare.asset.commonAsset.model.Enum.Title;
import lk.AVSEC.Welfare.asset.employee.controller.EmployeeRestController;
import lk.AVSEC.Welfare.asset.employee.entity.Enum.Designation;
import lk.AVSEC.Welfare.asset.employee.entity.Enum.EmployeeStatus;
import lk.AVSEC.Welfare.asset.supplier.service.SupplierService;
import lk.AVSEC.Welfare.util.service.MakeAutoGenerateNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommonService {
    private final MakeAutoGenerateNumberService makeAutoGenerateNumberService;
    private final SupplierService supplierService;
    //common things to employee and offender - start
    public void commonUrlBuilder(Model model) {
        model.addAttribute("addStatus", true);
        model.addAttribute("designations", Designation.values());
/*        model.addAttribute("provinces", Province.values());
        model.addAttribute("districtUrl", MvcUriComponentsBuilder
                .fromMethodName(WorkingPlaceRestController.class, "getDistrict", "")
                .build()
                .toString());
        model.addAttribute("stationUrl", MvcUriComponentsBuilder
                .fromMethodName(WorkingPlaceRestController.class, "getStation", "")
                .build()
                .toString());*/
        Object[] arg = {"designation", "id"};
        model.addAttribute("employeeUrl", MvcUriComponentsBuilder
                .fromMethodName(EmployeeRestController.class, "getEmployeeByWorkingPlace", arg)
                .build()
                .toString());
    }

    public void commonEmployeeAndOffender(Model model) {
        model.addAttribute("title", Title.values());
        model.addAttribute("gender", Gender.values());
        model.addAttribute("civilStatus", CivilStatus.values());
        model.addAttribute("employeeStatus", EmployeeStatus.values());
        model.addAttribute("designation", Designation.values());
        model.addAttribute("bloodGroup", BloodGroup.values());
    }

    //common things to employee and offender - end
    //common mobile number length employee,offender,guardian, petitioner - start
    // private final mobile length validator
    public String commonMobileNumberLengthValidator(String number) {
        if ( number.length() == 9 ) {
            number = "0".concat(number);
        }
        return number;
    }

    @Autowired
    public CommonService(MakeAutoGenerateNumberService makeAutoGenerateNumberService, SupplierService supplierService) {
        this.makeAutoGenerateNumberService = makeAutoGenerateNumberService;
        this.supplierService = supplierService;
    }
    public String supplierItemAndPurchaseOrderSearch(Supplier supplier, Model model, String htmlFileLocation) {
        List<Supplier> suppliers;
        if (supplier.getContactOne() != null) {
            String contactNumber = makeAutoGenerateNumberService.phoneNumberLengthValidator(supplier.getContactOne());
//all match with supplier contact number one
            supplier.setContactOne(contactNumber);
            supplier.setContactTwo(null);
            suppliers = new ArrayList<>(supplierService.search(supplier));
//all match with contact number two
            supplier.setContactOne(null);
            supplier.setContactTwo(contactNumber);
            suppliers.addAll(supplierService.search(supplier));

        } else {
            suppliers = supplierService.search(supplier);
        }
        if (supplier.getContactOne() != null) {
            suppliers = suppliers.stream()
                    .filter(supplier1 ->
                            supplier.getContactOne().equals(supplier1.getContactTwo()) ||
                                    supplier.getContactOne().equals(supplier1.getContactOne()))
                    .collect(Collectors.toList());
        }
        model.addAttribute("searchAreaShow", false);
        if (suppliers.size() == 1) {
            model.addAttribute("supplierDetail", suppliers.get(0));
            model.addAttribute("supplierDetailShow", false);
            return htmlFileLocation;
        }
        model.addAttribute("suppliers", suppliers);
        model.addAttribute("supplierDetailShow", true);
        return htmlFileLocation;
    }

    public void supplierItemAndPurchaseOrderView(Model model, Integer id) {
        model.addAttribute("searchAreaShow", false);
        model.addAttribute("supplierDetail", supplierService.findById(id));
        model.addAttribute("supplierDetailShow", false);
    }


}
