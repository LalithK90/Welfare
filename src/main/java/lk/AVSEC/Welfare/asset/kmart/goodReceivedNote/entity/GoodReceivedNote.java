package lk.AVSEC.Welfare.asset.kmart.goodReceivedNote.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import lk.AVSEC.Welfare.asset.kmart.goodReceivedNote.entity.Enum.GoodReceivedNoteState;
import lk.AVSEC.Welfare.asset.kmart.purchaseOrder.entity.PurchaseOrder;
import lk.AVSEC.Welfare.util.audit.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonFilter("GoodReceivedNote")
public class GoodReceivedNote extends AuditEntity {
    @ManyToOne
    private PurchaseOrder purchaseOrder;

    @Column(precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    private GoodReceivedNoteState goodReceivedNoteState;

    private String remarks;

}
