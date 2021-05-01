package lk.avsec_welfare.asset.death_donation.entity;

import lk.avsec_welfare.asset.dependent.entity.Dependent;
import lk.avsec_welfare.asset.employee.entity.Employee;
import lk.avsec_welfare.util.audit.AuditEntity;
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
public class DeathDonation extends AuditEntity {

  @Column(nullable = false, precision = 10, scale = 2)
  private BigDecimal amount;

  private String remark;

  @ManyToOne
  private Employee employee;

  @ManyToOne
  private Dependent dependent;
}
