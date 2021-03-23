package lk.AVSEC.Welfare.asset.common_asset.service;

import lk.AVSEC.Welfare.asset.employee.controller.EmployeeRestController;
import lk.AVSEC.Welfare.asset.a_shop_management.item.entity.Item;
import lk.AVSEC.Welfare.asset.a_shop_management.item.service.ItemService;
import lk.AVSEC.Welfare.asset.a_shop_management.supplier.entity.Supplier;
import lk.AVSEC.Welfare.asset.a_shop_management.supplier.service.SupplierService;
import lk.AVSEC.Welfare.asset.a_shop_management.supplierItem.entity.Enum.ItemSupplierStatus;
import lk.AVSEC.Welfare.asset.a_shop_management.supplierItem.entity.SupplierItem;
import lk.AVSEC.Welfare.asset.a_shop_management.supplierItem.service.SupplierItemService;
import lk.AVSEC.Welfare.util.service.MakeAutoGenerateNumberService;
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
       private final ItemService itemService;
       private final SupplierItemService supplierItemService;

       public CommonService(MakeAutoGenerateNumberService makeAutoGenerateNumberService, SupplierService supplierService, ItemService itemService, SupplierItemService supplierItemService) {
           this.makeAutoGenerateNumberService = makeAutoGenerateNumberService;
           this.supplierService = supplierService;
           this.itemService = itemService;
           this.supplierItemService = supplierItemService;
       }

       public List<Supplier> commonSupplierSearch(Supplier supplier) {
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
           return suppliers;
       }

       public String supplierItem(Supplier supplier, Model model, String htmlFileLocation) {
           List<Supplier> suppliers = commonSupplierSearch(supplier);

           model.addAttribute("searchAreaShow", false);

           if (suppliers.size() == 1) {
               model.addAttribute("supplierDetail", suppliers.get(0));
               return "redirect:/supplierItem/supplier/" + suppliers.get(0).getId();
           }
           model.addAttribute("suppliers", suppliers);
           model.addAttribute("supplierDetailShow", true);
           return htmlFileLocation;
       }

       public String purchaseOrder(Supplier supplier, Model model, String htmlFileLocation) {
           List<Supplier> suppliers = commonSupplierSearch(supplier);

           System.out.println(" i am here" + suppliers.size());

           model.addAttribute("searchAreaShow", false);
           if (suppliers.size() == 1) {
               model.addAttribute("supplierDetail", suppliers.get(0));
               model.addAttribute("items", activeItemsFromSupplier(suppliers.get(0)));
               model.addAttribute("purchaseOrderItemEdit", false);
               return "redirect:/purchaseOrder/supplier/" + suppliers.get(0).getId();
           }
           model.addAttribute("suppliers", suppliers);
           model.addAttribute("supplierDetailShow", true);
           return htmlFileLocation;
       }

       public String supplierItem(Model model, Integer id) {
           model.addAttribute("searchAreaShow", false);
           model.addAttribute("supplierDetail", supplierService.findById(id));
           model.addAttribute("supplierDetailShow", false);
           model.addAttribute("items", itemService.findAll());
           return "supplier/addSupplierItem";
       }

       public String purchaseOrder(Model model, Integer id) {
           Supplier supplier = supplierService.findById(id);
           model.addAttribute("searchAreaShow", false);
           model.addAttribute("supplierDetail", supplier);
           model.addAttribute("items", activeItemsFromSupplier(supplier));
           model.addAttribute("supplierDetailShow", false);
           return "purchaseOrder/addPurchaseOrder";
       }

       public String commonMobileNumberLengthValidator(String mobileTwo) {
           return mobileTwo;
       }

       public List<Item> activeItemsFromSupplier(Supplier supplier) {
           List<SupplierItem> supplierItems = supplierItemService.findBySupplierAndItemSupplierStatus(supplier, ItemSupplierStatus.CURRENTLY_BUYING);
           List<Item> items = new ArrayList<>();
           for (SupplierItem supplierItem : supplierItems) {
               items.add(supplierItem.getItem());
           }
           return items;
       }

       //common things to employee and offender - start
       public void commonUrlBuilder(Model model) {
           model.addAttribute("addStatus", true);
           // model.addAttribute("designations", Designation.values());
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

   }
