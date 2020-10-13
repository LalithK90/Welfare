package lk.AVSEC.Welfare.asset.promotion.controller;

import lk.AVSEC.Welfare.asset.commonAsset.model.Enum.Province;
import lk.AVSEC.Welfare.asset.employee.service.EmployeeService;
import lk.AVSEC.Welfare.asset.promotion.entity.Promotion;
import lk.AVSEC.Welfare.asset.promotion.service.PromotionService;
import lk.AVSEC.Welfare.util.interfaces.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping( "/promotion" )
public class PromotionController {

  private final PromotionService promotionService;
  private final EmployeeService employeeService;

  @Autowired
  public PromotionController(PromotionService promotionService, EmployeeService employeeService) {
    this.promotionService = promotionService;
    this.employeeService = employeeService;
  }

  private String commonThing(Model model, Boolean booleanValue, Promotion promotionObject) {
    model.addAttribute("promotions", Province.values());
    model.addAttribute("addStatus", booleanValue);
    model.addAttribute("promotion", promotionObject);
    return "promotion/addPromotion";
  }

  @GetMapping
  public String findAll(Model model) {
    model.addAttribute("promotions", promotionService.findAll());
    return "promotion/promotion";
  }

  @GetMapping( "/add/{id}" )
  public String promotionAddEmployee(@PathVariable Integer id, Model model) {
    model.addAttribute("employee", employeeService.findById(id));
    return commonThing(model, true, new Promotion());
  }

  @GetMapping( "/{id}" )
  public String findById(@PathVariable Integer id, Model model) {
    model.addAttribute("promotionDetail", promotionService.findById(id));
    return "promotion/promotion-detail";
  }

  @GetMapping( "/edit/{id}" )
  public String edit(@PathVariable Integer id, Model model) {
    return commonThing(model, true, promotionService.findById(id));
  }

  @PostMapping( value = {"/save", "/update"} )
  public String persist(@Valid @ModelAttribute Promotion promotion, BindingResult bindingResult,
                        RedirectAttributes redirectAttributes, Model model) {
    if ( bindingResult.hasErrors() ) {
      return commonThing(model, false, promotion);

    }
    redirectAttributes.addFlashAttribute("promotionDetail", promotionService.persist(promotion));
    return "redirect:/promotion";
  }

  @GetMapping( "/delete/{id}" )
  public String delete(@PathVariable Integer id, Model model) {
    promotionService.delete(id);
    return "redirect:/promotion";
  }


}
