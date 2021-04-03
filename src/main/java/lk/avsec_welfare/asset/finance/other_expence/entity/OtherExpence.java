package lk.avsec_welfare.asset.finance.other_expence.entity;

import lk.avsec_welfare.asset.finance.other_expence.entity.enums.OtherExpenceType;
import lk.avsec_welfare.util.audit.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OtherExpence extends AuditEntity {

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    private String remark;

   @Enumerated(EnumType.STRING)
    private OtherExpenceType otherExpenceType;


}
