package lk.AVSEC.Welfare.asset.employee.entity;


import com.fasterxml.jackson.annotation.JsonFilter;
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
@JsonFilter("Promotion")
public class Promotion extends AuditEntity {



  @Size(min = 2, max = 60, message = "Your name length should be 13")
    private String name;



    private String remark;

   @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate withEffect;



}
