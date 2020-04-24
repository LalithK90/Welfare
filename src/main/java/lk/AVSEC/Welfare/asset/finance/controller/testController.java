package lk.AVSEC.Welfare.asset.finance.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class testController {
    @GetMapping("/feeType")
    public String template(){
        return "finance/feeType";
    }

}


