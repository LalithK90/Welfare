package lk.avsec_welfare.asset.qualification.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import lk.avsec_welfare.asset.employee.entity.Employee;
import lk.avsec_welfare.util.audit.AuditEntity;
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
@JsonFilter("Qualification")
public class Qualification extends AuditEntity {

    @Size(min = 2, max = 60, message = "Your name length should be 13")
    private String name;

    private String institute;

    private String grade;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate completeDate;

    @Enumerated(EnumType.STRING)
    private LiveDead liveDead;


    @ManyToOne
    private Employee employee;

}
