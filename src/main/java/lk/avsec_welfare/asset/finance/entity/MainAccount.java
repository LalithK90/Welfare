package lk.avsec_welfare.asset.finance.entity;

import lk.avsec_welfare.asset.finance.entity.Enum.ExpenseOrReceived;
import lk.avsec_welfare.asset.finance.entity.Enum.OtherFundReceivingType;
import lk.avsec_welfare.util.audit.AuditEntity;
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
public class MainAccount extends AuditEntity {

    private String type;

    private String remark;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private ExpenseOrReceived expenseOrReceived;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Instalment instalment;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private OtherFundReceiving otherFundReceiving;

    @Transient
    private OtherFundReceivingType otherFundReceivingType;

    @Transient
    private InstalmentType instalmentType;



}
