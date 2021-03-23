package lk.AVSEC.Welfare.asset.a_shop_management.payment.controller;


import lk.AVSEC.Welfare.asset.a_shop_management.goodReceivedNote.entity.Enum.GoodReceivedNoteState;
import lk.AVSEC.Welfare.asset.a_shop_management.goodReceivedNote.entity.GoodReceivedNote;
import lk.AVSEC.Welfare.asset.a_shop_management.goodReceivedNote.service.GoodReceivedNoteService;
import lk.AVSEC.Welfare.asset.a_shop_management.invoice.entity.Enum.PaymentMethod;
import lk.AVSEC.Welfare.asset.a_shop_management.payment.entity.Payment;
import lk.AVSEC.Welfare.asset.a_shop_management.payment.service.PaymentService;
import lk.AVSEC.Welfare.util.service.MakeAutoGenerateNumberService;
import lk.AVSEC.Welfare.util.service.OperatorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping( "/payment" )
public class PaymentController {

    private final PaymentService paymentService;
    private final lk.AVSEC.Welfare.asset.a_shop_management.PurchaseOrder.service.PurchaseOrderService purchaseOrderService;
    private final OperatorService operatorService;
    private final GoodReceivedNoteService goodReceivedNoteService;
    private final MakeAutoGenerateNumberService makeAutoGenerateNumberService;

    public PaymentController(PaymentService paymentService, lk.AVSEC.Welfare.asset.a_shop_management.PurchaseOrder.service.PurchaseOrderService purchaseOrderService,
                             OperatorService operatorService, GoodReceivedNoteService goodReceivedNoteService,
                             MakeAutoGenerateNumberService makeAutoGenerateNumberService) {
        this.paymentService = paymentService;
        this.purchaseOrderService = purchaseOrderService;
        this.operatorService = operatorService;
        this.goodReceivedNoteService = goodReceivedNoteService;
        this.makeAutoGenerateNumberService = makeAutoGenerateNumberService;
    }


    @GetMapping
    public String getAllPurchaseOrderToPay(Model model) {
        //find all purchase order to have to pay using purchase order status
        //1. still not processed po 2. partially paid po
        List<lk.AVSEC.Welfare.asset.a_shop_management.PurchaseOrder.entity.PurchaseOrder> purchaseOrdersDB =
                purchaseOrderService.findByPurchaseOrderStatus(lk.AVSEC.Welfare.asset.a_shop_management.PurchaseOrder.entity.Enum.PurchaseOrderStatus.NOT_PROCEED);
        if ( !purchaseOrdersDB.isEmpty() ) {
            //need to pay po
            List<lk.AVSEC.Welfare.asset.a_shop_management.PurchaseOrder.entity.PurchaseOrder> purchaseOrders = new ArrayList<>();

            for ( lk.AVSEC.Welfare.asset.a_shop_management.PurchaseOrder.entity.PurchaseOrder purchaseOrder : purchaseOrdersDB ) {
                List<Payment> payments = paymentService.findByPurchaseOrder(purchaseOrder);
                if ( payments != null ) {
                    BigDecimal paidAmount = BigDecimal.ZERO;
                    for ( Payment payment : payments ) {
                        paidAmount = operatorService.addition(paidAmount, payment.getAmount());
                    }
                    purchaseOrder.setPrice(operatorService.subtraction(purchaseOrder.getPrice(), paidAmount));
                }
                purchaseOrders.add(purchaseOrder);
            }

            model.addAttribute("purchaseOrders", purchaseOrders);
        }
        return "payment/payment";
    }

    @GetMapping( "/{id}" )
    public String addPaymentAmount(@PathVariable( "id" ) Integer id, Model model) {
        //payment need to make
        lk.AVSEC.Welfare.asset.a_shop_management.PurchaseOrder.entity.PurchaseOrder purchaseOrderNeedToPay = purchaseOrderService.findById(id);

        //1. still not processed po 2. partially paid po
        List<lk.AVSEC.Welfare.asset.a_shop_management.PurchaseOrder.entity.PurchaseOrder> purchaseOrdersDB =
                purchaseOrderService.findByPurchaseOrderStatusAndSupplier(lk.AVSEC.Welfare.asset.a_shop_management.PurchaseOrder.entity.Enum.PurchaseOrderStatus.NOT_PROCEED,
                                                                          purchaseOrderNeedToPay.getSupplier());
        List<lk.AVSEC.Welfare.asset.a_shop_management.PurchaseOrder.entity.PurchaseOrder> purchaseOrderNotPaid = new ArrayList<>();

        if ( purchaseOrdersDB != null ) {
            for ( lk.AVSEC.Welfare.asset.a_shop_management.PurchaseOrder.entity.PurchaseOrder purchaseOrder : purchaseOrdersDB ) {
                List< Payment > payments = paymentService.findByPurchaseOrder(purchaseOrder);
                if ( payments != null ) {
                    BigDecimal paidAmount = BigDecimal.ZERO;
                    for ( Payment payment : payments ) {
                        paidAmount = operatorService.addition(paidAmount, payment.getAmount());
                    }
                    if ( goodReceivedNoteService.findByPurchaseOrder(purchaseOrder) != null ) {
                        purchaseOrder.setGrnAt(goodReceivedNoteService.findByPurchaseOrder(purchaseOrder).getCreatedAt());
                    } else {
                        purchaseOrder.setGrnAt(LocalDateTime.now());
                    }
                    purchaseOrder.setPaidAmount(paidAmount);
                    purchaseOrderNeedToPay.setNeedToPaid(operatorService.subtraction(purchaseOrder.getPrice(), paidAmount));
                }
                purchaseOrderNotPaid.add(purchaseOrder);
            }
        }
        model.addAttribute("payment", new Payment());
        model.addAttribute("purchaseOrders", purchaseOrderNotPaid);
        model.addAttribute("purchaseOrderNeedToPay", purchaseOrderNeedToPay);
        model.addAttribute("paymentMethods", PaymentMethod.values());
        return "payment/addPayment";
    }

    @PostMapping
    public String savePayment(@Valid @ModelAttribute Payment payment, BindingResult bindingResult) {
        if ( bindingResult.hasErrors() ) {
            return "redirect:/payment/".concat(String.valueOf(payment.getPurchaseOrder().getId()));
        }
        if ( payment.getId() == null ) {
            if ( paymentService.lastPayment() == null ) {
                //need to generate new one
                payment.setCode("JNPM" + makeAutoGenerateNumberService.numberAutoGen(null).toString());
            } else {
                System.out.println("last customer not null");
                //if there is customer in db need to get that customer's code and increase its value
                String previousCode = paymentService.lastPayment().getCode().substring(4);
                payment.setCode("JNPM" + makeAutoGenerateNumberService.numberAutoGen(previousCode).toString());
            }
        }

        //1. need to save payment
        Payment paymentDB = paymentService.persist(payment);
        lk.AVSEC.Welfare.asset.a_shop_management.PurchaseOrder.entity.PurchaseOrder purchaseOrder = paymentDB.getPurchaseOrder();
        BigDecimal purchaseOrderPrice = purchaseOrderService.findById(purchaseOrder.getId()).getPrice();
        //2. check po state -> need to finished all payment to change this
        //3. check grn state -> need to finished all payment to change this
        List< Payment > payments = paymentService.findByPurchaseOrder(purchaseOrder);
        if ( !payments.isEmpty() ) {
            BigDecimal paidAmount = BigDecimal.ZERO;
            for ( Payment paymentOne : payments ) {
                paidAmount = operatorService.addition(paidAmount, paymentOne.getAmount());
            }
            // if check all paid amount is equal or not purchase order amount

            if ( paidAmount.equals(purchaseOrderPrice) ) {
                System.out.println("Im here ");
                //change GRN sate
                GoodReceivedNote goodReceivedNote = goodReceivedNoteService.findByPurchaseOrder(purchaseOrder);
                goodReceivedNote.setGoodReceivedNoteState(GoodReceivedNoteState.PAID);
                goodReceivedNoteService.persist(goodReceivedNote);
                //change purchase order status
                lk.AVSEC.Welfare.asset.a_shop_management.PurchaseOrder.entity.PurchaseOrder completedPurchaseOrder =purchaseOrderService.findById(purchaseOrder.getId());
                completedPurchaseOrder.setPurchaseOrderStatus(lk.AVSEC.Welfare.asset.a_shop_management.PurchaseOrder.entity.Enum.PurchaseOrderStatus.COMPLETED);
                purchaseOrderService.persist(completedPurchaseOrder);
            }
        }
        return "redirect:/payment";
    }

}
