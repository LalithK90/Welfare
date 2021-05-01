package lk.avsec_welfare.asset.instalment.entity;

import lk.avsec_welfare.asset.common_asset.model.enums.LiveDead;
import lk.avsec_welfare.asset.employee.entity.Employee;
import lk.avsec_welfare.asset.installment_type.entity.InstalmentType;
import lk.avsec_welfare.asset.instalment.entity.enums.InstalmentStatus;
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
public class Instalment extends AuditEntity {

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private InstalmentStatus instalmentStatus;

    @ManyToOne
    private InstalmentType instalmentType;

    @ManyToOne
    private Employee employee;

    @Enumerated(EnumType.STRING)
    private LiveDead liveDead;

}
