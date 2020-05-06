package lk.AVSEC.Welfare.asset.supplier.controller;

import lk.AVSEC.Welfare.asset.commonAsset.service.CommonService;
import lk.AVSEC.Welfare.asset.item.service.ItemService;
import lk.AVSEC.Welfare.asset.supplier.entity.Supplier;
import lk.AVSEC.Welfare.asset.supplier.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/supplierItem")
public class SupplierItemController {
    private final ItemService itemService;
    private final SupplierService supplierService;
    private final CommonService commonService;

    @Autowired
    public SupplierItemController(ItemService itemService, SupplierService supplierService, CommonService commonService) {
        this.itemService = itemService;
        this.supplierService = supplierService;
        this.commonService = commonService;
    }

    @GetMapping
    public String addForm(Model model) {
        model.addAttribute("supplier", new Supplier());
        model.addAttribute("searchAreaShow", true);
        return "supplier/addSupplierItem";
    }

    @PostMapping("/find")
    public String search(@Valid @ModelAttribute Supplier supplier, Model model) {
        return commonService
                .supplierItemAndPurchaseOrderSearch(supplier, model, "supplier/addSupplierItem");
    }

    @GetMapping("/{id}")
    public String view(@PathVariable Integer id, Model model) {
        commonService.supplierItemAndPurchaseOrderView(model, id);
        model.addAttribute("items", itemService.findAll());
        return "supplier/addSupplierItem";
    }

    @PostMapping
    public String supplierItemPersist(@Valid @ModelAttribute Supplier supplier, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "redirect:/supplierItem/" + supplier.getId();
        }
        redirectAttributes.addFlashAttribute("items", supplierService.persist(supplier).getItems());
        return "redirect:/supplier";
    }

}
