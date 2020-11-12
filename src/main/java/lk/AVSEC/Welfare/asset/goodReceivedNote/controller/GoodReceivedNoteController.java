package lk.AVSEC.Welfare.asset.goodReceivedNote.controller;


import lk.AVSEC.Welfare.asset.goodReceivedNote.entity.Enum.GoodReceivedNoteState;
import lk.AVSEC.Welfare.asset.goodReceivedNote.entity.GoodReceivedNote;
import lk.AVSEC.Welfare.asset.goodReceivedNote.service.GoodReceivedNoteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping( "/goodReceivedNote" )
public class GoodReceivedNoteController {
    private final GoodReceivedNoteService goodReceivedNoteService;
    private final lk.AVSEC.Welfare.asset.PurchaseOrder.service.PurchaseOrderService purchaseOrderService;
    private final lk.AVSEC.Welfare.asset.ledger.service.LedgerService ledgerService;

    public GoodReceivedNoteController(GoodReceivedNoteService goodReceivedNoteService,
                                      lk.AVSEC.Welfare.asset.PurchaseOrder.service.PurchaseOrderService purchaseOrderService, lk.AVSEC.Welfare.asset.ledger.service.LedgerService ledgerService) {
        this.goodReceivedNoteService = goodReceivedNoteService;
        this.purchaseOrderService = purchaseOrderService;
        this.ledgerService = ledgerService;
    }


    @GetMapping
    public String notCompleteAll(Model model) {
        model.addAttribute("notCompleteAll",
                           purchaseOrderService.findByPurchaseOrderStatus(lk.AVSEC.Welfare.asset.PurchaseOrder.entity.Enum.PurchaseOrderStatus.NOT_COMPLETED));
        return "goodReceivedNote/goodReceivedNote";
    }

    @GetMapping( "/{id}" )
    public String grnAdd(@PathVariable Integer id, Model model) {
        model.addAttribute("purchaseOrderDetail", purchaseOrderService.findById(id));
        model.addAttribute("goodReceivedNote", new GoodReceivedNote());
        return "goodReceivedNote/addGoodReceivedNote";
    }

    @PostMapping( "/received" )
    public String saveGRN(@Valid @ModelAttribute GoodReceivedNote goodReceivedNote, BindingResult bindingResult,
                          RedirectAttributes redirectAttributes, Model model) {
        if ( bindingResult.hasErrors() ) {
            return "redirect:/goodReceivedNote/".concat(String.valueOf(goodReceivedNote.getPurchaseOrder().getId()));
        }
//New Leger add to add system as new item on ledger
        List<lk.AVSEC.Welfare.asset.ledger.entity.Ledger> ledgers = new ArrayList<>();
        for ( lk.AVSEC.Welfare.asset.ledger.entity.Ledger ledger : goodReceivedNote.getLedgers() ) {
            lk.AVSEC.Welfare.asset.ledger.entity.Ledger ledgerDB = ledgerService.findByItemAndAndExpiredDateAndSellPrice(ledger.getItem(),
                                                                                    ledger.getExpiredDate(),
                                                                                    ledger.getItem().getSellPrice());
//if there is on value in ledger need to update it
            if ( ledgerDB != null ) {
                //before update need to check price and expire date
                if ( ledgerDB.getExpiredDate() == ledger.getExpiredDate() && ledgerDB.getSellPrice().equals(ledger.getSellPrice()) ) {
                    ledgerDB.setQuantity(ledgerDB.getQuantity() + ledger.getQuantity());
                    ledgerDB.setGoodReceivedNote(goodReceivedNote);
                    ledgers.add(ledgerDB);
                } else {
                    ledger.setGoodReceivedNote(goodReceivedNote);
                    ledgers.add(ledger);
                }
            }
            ledger.setGoodReceivedNote(goodReceivedNote);
            ledgers.add(ledger);

        }
        goodReceivedNote.setGoodReceivedNoteState(GoodReceivedNoteState.NOT_PAID);
        goodReceivedNote.setLedgers(ledgers);

        lk.AVSEC.Welfare.asset.PurchaseOrder.entity.PurchaseOrder purchaseOrder = purchaseOrderService.findById(goodReceivedNote.getPurchaseOrder().getId());
        purchaseOrder.setPurchaseOrderStatus(lk.AVSEC.Welfare.asset.PurchaseOrder.entity.Enum.PurchaseOrderStatus.NOT_PROCEED);

        purchaseOrderService.persist(purchaseOrder);
        goodReceivedNoteService.persist(goodReceivedNote);
        return "redirect:/goodReceivedNote";
    }

}
