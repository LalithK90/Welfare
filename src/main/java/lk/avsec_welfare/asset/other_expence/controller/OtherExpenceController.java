package lk.avsec_welfare.asset.other_expence.controller;



import lk.avsec_welfare.asset.main_account.entity.Enum.FundType;
import lk.avsec_welfare.asset.main_account.entity.MainAccount;
import lk.avsec_welfare.asset.other_expence.entity.OtherExpence;
import lk.avsec_welfare.asset.other_expence.entity.enums.OtherExpenseType;
import lk.avsec_welfare.asset.other_expence.service.OtherExpenceService;
import lk.avsec_welfare.asset.main_account.service.MainAccountService;
import lk.avsec_welfare.util.service.OperatorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping( "/otherExpence" )
public class OtherExpenceController {
  private final OtherExpenceService otherExpenceService;
  private final MainAccountService mainAccountService;
  private  final OperatorService operatorService;


  public OtherExpenceController(OtherExpenceService otherExpenceService,
                                MainAccountService mainAccountService, OperatorService operatorService) {
    this.otherExpenceService = otherExpenceService;
    this.mainAccountService = mainAccountService;
    this.operatorService = operatorService;
  }

  @GetMapping
  public String findAll(Model model) {
    model.addAttribute("otherExpenceTypes", otherExpenceService.findAll());
    model.addAttribute("contendHeader", "Other Expences Details");
    return "otherExpence/otherExpence";
  }

  @GetMapping( "/add" )
  public String addFrom(Model model) {
    model.addAttribute("otherExpence", new OtherExpence());
    model.addAttribute("otherExpenceTypes", OtherExpenseType.values());
    model.addAttribute("contendHeader", "Add Other Expences");
    return "otherExpence/addOtherExpence";
  }

  @PostMapping
  public String persist(@ModelAttribute OtherExpence otherExpence, BindingResult bindingResult){
    if ( bindingResult.hasErrors() ){
      return "redirect:/otherExpence/add";
    }
    OtherExpence otherExpenceDb = otherExpenceService.persist(otherExpence);

    MainAccount mainAccount = mainAccountService.findByFundType(FundType.OTHEXP);
    if ( mainAccount == null ) {
      mainAccount = new MainAccount();
      mainAccount.setAmount(otherExpenceDb.getAmount());
      mainAccount.setFundType(FundType.OTHEXP);
    } else {
      mainAccount.setAmount(operatorService.addition(otherExpenceDb.getAmount(), mainAccount.getAmount()));
    }
    mainAccountService.persist(mainAccount);
    return "redirect:/otherExpence";
  }
}
