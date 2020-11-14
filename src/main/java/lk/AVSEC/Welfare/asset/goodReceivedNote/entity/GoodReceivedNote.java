package lk.AVSEC.Welfare.asset.goodReceivedNote.entity;


import com.fasterxml.jackson.annotation.JsonFilter;
import lk.AVSEC.Welfare.asset.goodReceivedNote.entity.Enum.GoodReceivedNoteState;
import lk.AVSEC.Welfare.util.audit.AuditEntity;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonFilter( "GoodReceivedNote" )
@ToString
public class GoodReceivedNote extends AuditEntity {

    private String remarks;

    @Column( precision = 10, scale = 2 )
    private BigDecimal totalAmount;

    @Enumerated( EnumType.STRING )
    private GoodReceivedNoteState goodReceivedNoteState;

    @ManyToOne
    private lk.AVSEC.Welfare.asset.PurchaseOrder.entity.PurchaseOrder purchaseOrder;

    @OneToMany( mappedBy = "goodReceivedNote", cascade = CascadeType.PERSIST)
    private List<lk.AVSEC.Welfare.asset.ledger.entity.Ledger> ledgers;


}
