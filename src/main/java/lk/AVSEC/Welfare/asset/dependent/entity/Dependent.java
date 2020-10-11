package lk.AVSEC.Welfare.asset.dependent.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import lk.AVSEC.Welfare.asset.dependent.entity.Enum.CurrentStatus;
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
@JsonFilter("Dependent")
public class Dependent extends AuditEntity {

    private String name;

    private String nic;

    private String remark;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dob;

    @Enumerated(EnumType.STRING)
    private CurrentStatus currentStatus;

    @OneToMany(mappedBy = "dependent")
    private List<DependentEmployee> dependentEmployees;

    @Transient
    @Enumerated(EnumType.STRING)
    private Relationship relationship;

    @Transient
    private String epfNumber;

    @Transient
    private Employee employee;
}
