package lk.AVSEC.Welfare.asset.finance.entity;

import lk.AVSEC.Welfare.asset.employee.entity.Employee;
import lk.AVSEC.Welfare.util.audit.AuditEntity;
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
}
