package lk.avsec_welfare.asset.promotion.controller;

import lk.avsec_welfare.asset.dependent.service.DependentEmployeeService;
import lk.avsec_welfare.asset.designation.service.DesignationService;
import lk.avsec_welfare.asset.employee.entity.Employee;
import lk.avsec_welfare.asset.employee.service.EmployeeFilesService;
import lk.avsec_welfare.asset.employee.service.EmployeeService;
import lk.avsec_welfare.asset.employee_working_place.service.EmployeeWorkingPlaceService;
import lk.avsec_welfare.asset.promotion.entity.Promotion;
import lk.avsec_welfare.asset.promotion.service.PromotionService;
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
  private final DesignationService designationService;
  private final EmployeeFilesService employeeFilesService;
  private final EmployeeWorkingPlaceService employeeWorkingPlaceService;
  private final DependentEmployeeService dependentEmployeeService;

  @Autowired
  public PromotionController(PromotionService promotionService, EmployeeService employeeService,
                             DesignationService designationService, EmployeeFilesService employeeFilesService,
                             EmployeeWorkingPlaceService employeeWorkingPlaceService,
                             DependentEmployeeService dependentEmployeeService) {
    this.promotionService = promotionService;
    this.employeeService = employeeService;
    this.designationService = designationService;
    this.employeeFilesService = employeeFilesService;
    this.employeeWorkingPlaceService = employeeWorkingPlaceService;
    this.dependentEmployeeService = dependentEmployeeService;
  }

  private String commonThing(Model model, Boolean booleanValue, Promotion promotionObject) {
    Employee employee = employeeService.findById(promotionObject.getEmployee().getId());
    model.addAttribute("files", employeeFilesService.employeeFileDownloadLinks(employee));
    model.addAttribute("employeeDetail", employee);
    model.addAttribute("employeeWorkingPlaces", employeeWorkingPlaceService.findByEmployee(employee));
    model.addAttribute("dependentEmployees", dependentEmployeeService.findByEmployee(employee));
    model.addAttribute("contendHeader", "Employee View Details");
    model.addAttribute("addStatus", booleanValue);
    model.addAttribute("promotion", promotionObject);
    model.addAttribute("designations", designationService.findAll());
    return "promotion/addPromotion";
  }

  @GetMapping
  public String findAll(Model model) {
    model.addAttribute("promotions", promotionService.findAll());
    return "promotion/promotion";
  }

  @GetMapping( "/add/{id}" )
  public String promotionAddEmployee(@PathVariable Integer id, Model model) {
    Promotion newPromotion = new Promotion();
    newPromotion.setEmployee(employeeService.findById(id));
//    8/11/2020
//    model.addAttribute("promotionDetail", promotionService.findById(id));
    return commonThing(model, false, newPromotion);
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
    Promotion promotionDb = promotionService.persist(promotion);
    Employee employeeDb = employeeService.findById(promotionDb.getEmployee().getId());
    employeeDb.setDesignation(promotionDb.getDesignation());
    employeeService.persist(employeeDb);
    redirectAttributes.addFlashAttribute("employees", employeeService.findAll());
    return "redirect:/employee";
  }

  @GetMapping( "/delete/{id}" )
  public String delete(@PathVariable Integer id, Model model) {
    promotionService.delete(id);
    return "redirect:/promotion";
  }


}
