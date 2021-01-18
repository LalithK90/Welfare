package lk.AVSEC.Welfare.asset.a_shop_management.PurchaseOrder.entity;


import com.fasterxml.jackson.annotation.JsonFilter;
import lk.AVSEC.Welfare.asset.a_shop_management.item.entity.Item;
import lk.AVSEC.Welfare.util.audit.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonFilter( "PurchaseOrderItem" )
public class PurchaseOrderItem extends AuditEntity {

    @Column( nullable = false )
    private String quantity;

    @Column( nullable = false, precision = 10, scale = 2 )
    private BigDecimal buyingPrice;

    @Column( nullable = false, precision = 10, scale = 2 )
    private BigDecimal lineTotal;

    @ManyToOne
    private PurchaseOrder purchaseOrder;

    @ManyToOne
    private Item item;

}
