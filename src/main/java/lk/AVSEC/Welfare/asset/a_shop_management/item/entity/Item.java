package lk.AVSEC.Welfare.asset.a_shop_management.item.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;

import com.fasterxml.jackson.annotation.JsonFilter;
import lk.AVSEC.Welfare.asset.a_shop_management.category.entity.Category;
import lk.AVSEC.Welfare.asset.a_shop_management.item.entity.Enum.ItemStatus;
import lk.AVSEC.Welfare.util.audit.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonFilter( "Item" )
public class Item extends AuditEntity {

    @Size( min = 5, message = "Your name cannot be accepted" )
    private String name;

    @NotEmpty
    private String rop;

    @Column( unique = true )
    private String code;

    @Column( nullable = false, precision = 10, scale = 2 )
    private BigDecimal sellPrice;

    @Enumerated( EnumType.STRING )
    private ItemStatus itemStatus;

    @ManyToOne
    private Category category;

    @OneToMany( mappedBy = "item" )
    private List<lk.AVSEC.Welfare.asset.a_shop_management.supplierItem.entity.SupplierItem> supplierItems;

    @OneToMany( mappedBy = "item" )
    @JsonBackReference
    private List<lk.AVSEC.Welfare.asset.a_shop_management.ledger.entity.Ledger> ledgers;

    @OneToMany( mappedBy = "item" )
    private List<lk.AVSEC.Welfare.asset.a_shop_management.PurchaseOrder.entity.PurchaseOrderItem> purchaseOrderItems;
}
