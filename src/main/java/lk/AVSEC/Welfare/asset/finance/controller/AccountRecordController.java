package lk.AVSEC.Welfare.asset.finance.controller;


import lk.AVSEC.Welfare.asset.finance.entity.Enum.ReceivingExpensesStatus;
import lk.AVSEC.Welfare.asset.finance.entity.ExpensesFund;
import lk.AVSEC.Welfare.asset.finance.entity.ReceivingFund;
import lk.AVSEC.Welfare.asset.finance.service.ReceivingFundService;
import lk.AVSEC.Welfare.asset.finance.service.ExpensesFundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import lk.AVSEC.Welfare.asset.account.service.AccountRecordService;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/account")
public class AccountRecordController {
    private final AccountRecordService accountRecordService;









    private final ReceivingFundService receivingFundService;
    private final ExpensesFundService expensesFundService;

    @Autowired
    public AccountRecordController(AccountRecordService accountRecordService, ReceivingFundService receivingFundService, ExpensesFundService expensesFundService) {
        this.accountRecordService = accountRecordService;
        this.receivingFundService = receivingFundService;
        this.expensesFundService = expensesFundService;
    }

    //new fund receiving form
    @GetMapping("/receiving")
    public String fundReceiving(Model model) {
        model.addAttribute("receivingFund", new ReceivingFund());
        model.addAttribute("receivingFundStatus", ReceivingExpensesStatus.values());
        return "finance/fundReceiving";
    }

    @PostMapping("/receiving")
    public String fundReceivingSave(@Valid @ModelAttribute ReceivingFund receivingFund, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            //if has error
        }

        //if not error
        //if ReceivingExpensesState(Approved)
        //1.today account amount need to update table AccountRecord
        //2.set ReceivingExpensesState(AnyOther) in receivingFund table
        //3.set accountRecord ExpenseOrReceived to  (RECEIVED)
        //else
        //set ReceivingExpensesState(AnyOther except Approved)
        //save
        return "redirect:/account/receiving";
    }

    // new credit form
    @GetMapping("/expenses")
    public String fundExpenses(Model model) {
        model.addAttribute("expensesFund", new ReceivingFund());
        model.addAttribute("expensesFundStatus", ReceivingExpensesStatus.values());
        return "finance/fundExpenses";
    }

    @PostMapping("/expenses")
    public String fundExpensesSave(@Valid @ModelAttribute ExpensesFund expensesFund, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            //if has error
        }

        //if not error
        //if ReceivingExpensesState(Approved)
        //1.today account amount need to update table AccountRecord
        //2.set ReceivingExpensesState(AnyOther)
        //3.set accountRecord ExpenseOrReceived to  (Expenses)
        //else
        //set ReceivingExpensesState(AnyOther except Approved)
        //save
        return "redirect:/account/expenses";
    }

    //balance show
    @GetMapping("/balance")
    public String balance(Model model) {
        // take all accountRecords
        //separate according to
        return "finance/balance";
    }

}
