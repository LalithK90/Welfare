package lk.avsec_welfare.asset.other_fund_receiving.controller;

import lk.avsec_welfare.asset.main_account.entity.Enum.FundType;
import lk.avsec_welfare.asset.main_account.entity.Enum.OtherFundReceivingType;
import lk.avsec_welfare.asset.main_account.entity.MainAccount;
import lk.avsec_welfare.asset.other_fund_receiving.entity.OtherFundReceiving;
import lk.avsec_welfare.asset.other_fund_receiving.service.OtherFundReceivingService;
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
@RequestMapping( "/otherFundReceiving" )
public class OtherFundReceivingController {
  private final OtherFundReceivingService otherFundReceivingService;
  private final MainAccountService mainAccountService;
  private  final OperatorService operatorService;


  public OtherFundReceivingController(OtherFundReceivingService otherFundReceivingService,
                                      MainAccountService mainAccountService, OperatorService operatorService) {
    this.otherFundReceivingService = otherFundReceivingService;
    this.mainAccountService = mainAccountService;
    this.operatorService = operatorService;
  }

  @GetMapping
  public String findAll(Model model) {
    model.addAttribute("otherFundReceivings", otherFundReceivingService.findAll());
    model.addAttribute("contendHeader", "Other Fund Receiving Details");
    return "otherFundReceiving/otherFundReceiving";
  }

  @GetMapping( "/add" )
  public String addFrom(Model model) {
    model.addAttribute("otherFundReceiving", new OtherFundReceiving());
    model.addAttribute("otherFundReceivingTypes", OtherFundReceivingType.values());
    model.addAttribute("contendHeader", "Add Other Fund Receiving");
    return "otherFundReceiving/addOtherFundReceiving";
  }

  @PostMapping
  public String persist(@ModelAttribute OtherFundReceiving otherFundReceiving, BindingResult bindingResult){
    if ( bindingResult.hasErrors() ){
      return "redirect:/otherFundReceiving/add";
    }
    OtherFundReceiving otherFundReceivingDb = otherFundReceivingService.persist(otherFundReceiving);

    MainAccount mainAccount = mainAccountService.findByFundType(FundType.OTHER_FUN_RE);
    if ( mainAccount == null ) {
      mainAccount = new MainAccount();
      mainAccount.setAmount(otherFundReceivingDb.getAmount());
      mainAccount.setFundType(FundType.OTHER_FUN_RE);
    } else {
      mainAccount.setAmount(operatorService.addition(otherFundReceivingDb.getAmount(), mainAccount.getAmount()));
    }
    mainAccountService.persist(mainAccount);
    return "redirect:/otherFundReceiving";
  }
}
