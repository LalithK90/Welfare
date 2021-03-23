package lk.avsec_welfare.asset.finance.entity;

import lk.avsec_welfare.asset.common_asset.model.enums.LiveDead;
import lk.avsec_welfare.asset.employee.entity.Employee;
import lk.avsec_welfare.util.audit.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Instalment extends AuditEntity {

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @ManyToOne
    private InstalmentType instalmentType;

    @ManyToOne
    private Employee employee;

    @OneToMany(mappedBy = "instalment")
    private List<MainAccount> mainAccounts;

    @Enumerated(EnumType.STRING)
    private LiveDead liveDead;

}