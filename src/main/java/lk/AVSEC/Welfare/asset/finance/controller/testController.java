package lk.AVSEC.Welfare.asset.finance.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class testController {
    @GetMapping("/additionalPayment")
    public String template() {
        return "finance/additionalPayment";
    }

    @GetMapping("/addPaymentType")
    public String templateOne() {
        return "finance/addPaymentType";
    }

    @GetMapping("/balance")
    public String templateTwo() {
        return "finance/balance";
    }

    @GetMapping("/financialYears1")
    public String templateTree() {
        return "financialYear";
    }

    @GetMapping("/paymentsSection")
    public String templateFour() {
        return "finance/paymentSection";
    }

    @GetMapping("/paymentView")
    public String templateFive() {
        return "finance/paymentView";
    }

    @GetMapping("/receivingExpenses")
    public String templateSix() {
        return "finance/receivingExpenses";
    }

    @GetMapping("/setPayment")
    public String templateTen() {
        return "finance/setPayment";
    }

    @GetMapping("/feeType")
    public String templateSeven() {
        return "finance/feeType";
    }




}


