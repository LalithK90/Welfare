package lk.AVSEC.Welfare.asset.employee_working_place.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import lk.AVSEC.Welfare.asset.employee.entity.Employee;
import lk.AVSEC.Welfare.asset.employee_working_place.entity.enums.WorkingPlaceChangeReason;
import lk.AVSEC.Welfare.asset.working_place.entity.WorkingPlace;
import lk.AVSEC.Welfare.util.audit.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonFilter( "EmployeeWorkingPlace" )
public class EmployeeWorkingPlace extends AuditEntity {

  private String remarks;

  @Column( nullable = false )
  @DateTimeFormat( pattern = "yyyy-MM-dd" )
  private LocalDate startAt;

  @Column( nullable = false )
  @DateTimeFormat( pattern = "yyyy-MM-dd" )
  private LocalDate endAt;

  @Enumerated(EnumType.STRING)
  private WorkingPlaceChangeReason workingPlaceChangeReason;

  @ManyToOne
  private Employee employee;

  @ManyToOne
  private WorkingPlace workingPlace;


}
