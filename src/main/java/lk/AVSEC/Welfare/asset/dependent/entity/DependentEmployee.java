package lk.AVSEC.Welfare.asset.dependent.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import lk.AVSEC.Welfare.asset.dependent.entity.Enum.CurrentStatus;
import lk.AVSEC.Welfare.asset.dependent.entity.Enum.InsideOrOut;
import lk.AVSEC.Welfare.asset.dependent.entity.Enum.Relationship;
import lk.AVSEC.Welfare.asset.employee.entity.Employee;
import lk.AVSEC.Welfare.util.audit.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonFilter("DependentEmployee")
public class DependentEmployee extends AuditEntity {


    @Enumerated(EnumType.STRING)
    private Relationship relationship;

    @ManyToOne
    private Employee employeeOne;


    @ManyToOne(cascade = CascadeType.PERSIST)
    private Dependent dependent;

    @Enumerated(EnumType.STRING)
    private InsideOrOut insideOrOut;

}
