package lk.AVSEC.Welfare.asset.a_shop_management.invoice.entity;

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
@JsonFilter("InvoiceItem")
public class InvoiceItem extends AuditEntity {

    @Column(nullable = false)
    private String quantity;

    @Column( nullable = false, precision = 10, scale = 2 )
    private BigDecimal sellPrice;

    @ManyToOne
    private Item item;

    @ManyToOne
    private Invoice invoice;

}
