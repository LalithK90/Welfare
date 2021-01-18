package lk.AVSEC.Welfare.asset.a_shop_management.invoice.controller;


import lk.AVSEC.Welfare.asset.a_shop_management.customer.service.CustomerService;
import lk.AVSEC.Welfare.asset.a_shop_management.discountRatio.service.DiscountRatioService;
import lk.AVSEC.Welfare.asset.a_shop_management.invoice.entity.Enum.PaymentMethod;
import lk.AVSEC.Welfare.asset.a_shop_management.invoice.entity.Invoice;
import lk.AVSEC.Welfare.util.service.DateTimeAgeService;
import lk.AVSEC.Welfare.util.service.MakeAutoGenerateNumberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.stream.Collectors;

@Controller
@RequestMapping( "/invoice" )
public class InvoiceController {
    private final lk.AVSEC.Welfare.asset.a_shop_management.invoice.service.InvoiceService invoiceService;
    private final lk.AVSEC.Welfare.asset.a_shop_management.item.service.ItemService itemService;
    private final CustomerService customerService;
    private final lk.AVSEC.Welfare.asset.a_shop_management.ledger.service.LedgerService ledgerService;
    private final DateTimeAgeService dateTimeAgeService;
    private final DiscountRatioService discountRatioService;
    private final MakeAutoGenerateNumberService makeAutoGenerateNumberService;

    public InvoiceController(lk.AVSEC.Welfare.asset.a_shop_management.invoice.service.InvoiceService invoiceService, lk.AVSEC.Welfare.asset.a_shop_management.item.service.ItemService itemService, CustomerService customerService,
                             lk.AVSEC.Welfare.asset.a_shop_management.ledger.service.LedgerService ledgerService, DateTimeAgeService dateTimeAgeService,
                             DiscountRatioService discountRatioService,
                             MakeAutoGenerateNumberService makeAutoGenerateNumberService) {
        this.invoiceService = invoiceService;
        this.itemService = itemService;
        this.customerService = customerService;
        this.ledgerService = ledgerService;
        this.dateTimeAgeService = dateTimeAgeService;
        this.discountRatioService = discountRatioService;
        this.makeAutoGenerateNumberService = makeAutoGenerateNumberService;
    }

    @GetMapping
    public String invoice(Model model) {
        model.addAttribute("invoices",
                           invoiceService.findByCreatedAtIsBetween(dateTimeAgeService.dateTimeToLocalDateStartInDay(dateTimeAgeService.getPastDateByMonth(3)), dateTimeAgeService.dateTimeToLocalDateEndInDay(LocalDate.now())));
        model.addAttribute("firstInvoiceMessage", true);
        return "invoice/invoice";
    }

    @GetMapping( "/search" )
    public String invoiceSearch(@RequestAttribute( "startDate" ) LocalDate startDate,
                                @RequestAttribute( "endDate" ) LocalDate endDate, Model model) {
        model.addAttribute("invoices",
                           invoiceService.findByCreatedAtIsBetween(dateTimeAgeService.dateTimeToLocalDateStartInDay(startDate), dateTimeAgeService.dateTimeToLocalDateEndInDay(endDate)));
        model.addAttribute("firstInvoiceMessage", true);
        return "invoice/invoice";
    }

    private String common(Model model, Invoice invoice) {
        model.addAttribute("invoice", invoice);
        model.addAttribute("invoicePrintOrNots", lk.AVSEC.Welfare.asset.a_shop_management.invoice.entity.Enum.InvoicePrintOrNot.values());
        model.addAttribute("paymentMethods", PaymentMethod.values());
        model.addAttribute("customers", customerService.findAll());
        model.addAttribute("discountRatios", discountRatioService.findAll());
        //send not expired and not zero quantity
        model.addAttribute("ledgers", ledgerService.findAll()
                .stream()
                .filter(x -> 0 < Integer.parseInt(x.getQuantity()) && x.getExpiredDate().isBefore( LocalDate.now()))
                .collect(Collectors.toList()));
        return "invoice/addInvoice";
    }

    @GetMapping( "/add" )
    public String getInvoiceForm(Model model) {
        return common(model, new Invoice());
    }

    @GetMapping( "/{id}" )
    public String viewDetails(@PathVariable Integer id, Model model) {
        model.addAttribute("invoiceDetail", invoiceService.findById(id));
        return "invoice/invoice-detail";
    }

    @PostMapping
    public String persistInvoice(@Valid @ModelAttribute Invoice invoice, BindingResult bindingResult, Model model) {
        if ( bindingResult.hasErrors() ) {
            return common(model, invoice);
        }
        if ( invoice.getId() == null ) {
            if ( invoiceService.findByLastInvoice() == null ) {
                //need to generate new one
                invoice.setCode("JNPI" + makeAutoGenerateNumberService.numberAutoGen(null).toString());
            } else {
                System.out.println("last customer not null");
                //if there is customer in db need to get that customer's code and increase its value
                String previousCode = invoiceService.findByLastInvoice().getCode().substring(4);
                invoice.setCode("JNPI" + makeAutoGenerateNumberService.numberAutoGen(previousCode).toString());
            }
        }
        invoice.setInvoiceValidOrNot(lk.AVSEC.Welfare.asset.a_shop_management.invoice.entity.Enum.InvoiceValidOrNot.VALID);

        return "redirect:/invoice";
    }


    @GetMapping( "/remove/{id}" )
    public String removeInvoice(@PathVariable( "id" ) Integer id) {
        Invoice invoice = invoiceService.findById(id);
        invoice.setInvoiceValidOrNot(lk.AVSEC.Welfare.asset.a_shop_management.invoice.entity.Enum.InvoiceValidOrNot.NOTVALID);
        invoiceService.persist(invoice);
        return "redirect:/invoice";
    }
} 
