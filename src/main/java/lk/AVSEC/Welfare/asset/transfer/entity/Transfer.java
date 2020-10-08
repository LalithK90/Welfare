package lk.AVSEC.Welfare.asset.transfer.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import lk.AVSEC.Welfare.asset.grievances.entity.Enum.GrievancesStatus;
import lk.AVSEC.Welfare.asset.grievances.entity.Enum.Priority;
import lk.AVSEC.Welfare.asset.grievances.entity.Enum.SolutionType;
import lk.AVSEC.Welfare.asset.grievances.entity.GrievanceStateChange;
import lk.AVSEC.Welfare.asset.transfer.entity.Enum.Reason;
import lk.AVSEC.Welfare.util.audit.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonFilter("Reason")
public class Transfer extends AuditEntity {

    @DateTimeFormat( pattern = "yyyy-MM-dd" )
    private LocalDate dateFrom;

    @DateTimeFormat( pattern = "yyyy-MM-dd" )
    private LocalDate dateTo;

    private String description;

    @Enumerated(EnumType.STRING)
    private Reason reason;

//    @Transient
//    private String remark;

}
