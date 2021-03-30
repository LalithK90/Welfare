package lk.avsec_welfare.asset.finance.other_fund_receiving.controller;

import lk.avsec_welfare.asset.finance.other_fund_receiving.service.OtherFundReceivingService;
import lk.avsec_welfare.asset.finance.service.MainAccountService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/otherFundReceiving")
public class OtherFundReceivingController {
private final OtherFundReceivingService otherFundReceivingService;
private final MainAccountService mainAccountService;

  public OtherFundReceivingController(OtherFundReceivingService otherFundReceivingService,
                                      MainAccountService mainAccountService) {
    this.otherFundReceivingService = otherFundReceivingService;
    this.mainAccountService = mainAccountService;
  }

  @GetMapping
  public String findAll(Model model){
    model.addAttribute("otherFundReceivings", otherFundReceivingService.findAll());
    return "otherFundReceiving/otherFundReceiving";
  }
}
