package lk.avsec_welfare.asset.dependent.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import lk.avsec_welfare.asset.common_asset.model.enums.LiveDead;
import lk.avsec_welfare.asset.dependent.entity.Enum.InsideOrOut;
import lk.avsec_welfare.asset.dependent.entity.Enum.Relationship;
import lk.avsec_welfare.asset.employee.entity.Employee;
import lk.avsec_welfare.util.audit.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonFilter("DependentEmployee")
public class DependentEmployee extends AuditEntity {


    @Enumerated(EnumType.STRING)
    private Relationship relationship;

    @Enumerated(EnumType.STRING)
    private LiveDead liveDead;


    @ManyToOne
    private Employee employeeOne;


    @ManyToOne(cascade = CascadeType.PERSIST)
    private Dependent dependent;

    @Enumerated(EnumType.STRING)
    private InsideOrOut insideOrOut;

}
