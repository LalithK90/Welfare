package lk.AVSEC.Welfare.asset.a_shop_management.invoice.entity;


import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lk.AVSEC.Welfare.asset.a_shop_management.customer.entity.Customer;
import lk.AVSEC.Welfare.asset.a_shop_management.discountRatio.entity.DiscountRatio;
import lk.AVSEC.Welfare.asset.a_shop_management.invoice.entity.Enum.PaymentMethod;
import lk.AVSEC.Welfare.util.audit.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonFilter("Invoice")
@JsonIgnoreProperties(value = {"balance", "discountAmount", "bankName", "cardNumber"}, allowGetters = true)
public class Invoice extends AuditEntity {

    private String bankName;

    private String cardNumber;

    private String remarks;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalPrice;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @Column(precision = 10, scale = 2)
    private BigDecimal discountAmount;

    @Column(precision = 10, scale = 2)
    private BigDecimal amountTendered;

    @Column(precision = 10, scale = 2)
    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    private lk.AVSEC.Welfare.asset.a_shop_management.invoice.entity.Enum.InvoicePrintOrNot invoicePrintOrNot;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    private lk.AVSEC.Welfare.asset.a_shop_management.invoice.entity.Enum.InvoiceValidOrNot invoiceValidOrNot;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private DiscountRatio discountRatio;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "invoice")
    private List<lk.AVSEC.Welfare.asset.a_shop_management.invoice.entity.InvoiceItem> invoiceItemQuantities;


}
