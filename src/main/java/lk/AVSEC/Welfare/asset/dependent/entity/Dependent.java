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

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonFilter("Dependency")
public class Dependent extends AuditEntity {

    @Size(min = 2, max = 60, message = "Your name length should be 13")
    private String name;

    @Enumerated(EnumType.STRING)
    private Relationship relationship;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dob;

    private String nic;

    @Enumerated(EnumType.STRING)
    private CurrentStatus currentStatus;

    private String remark;

    @ManyToOne
    private Employee employee;
}
