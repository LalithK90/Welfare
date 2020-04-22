package lk.AVSEC.Welfare.asset.grievances.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import lk.AVSEC.Welfare.asset.grievances.entity.Enum.Priority;
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
@JsonFilter("Grievances")
public class Grievances extends AuditEntity {


   // @Size(min = 2, max = 60, message = "Your name length should be 13")
    private String name;

    private String description;

    private String participant;

@Enumerated (EnumType.STRING)
    private Priority priority;

    private String remark;



  /*  @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate completeDate;*/
    /*@Enumerated(EnumType.STRING)
    private Province province;*/

//Subject(name) , Description , participant , Remark, priority
/*    @OneToMany(mappedBy = "district")
    private List<WorkingPlace> workingPlaces;*/
}
