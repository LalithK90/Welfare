package lk.avsec_welfare.asset.transfer.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import lk.avsec_welfare.asset.transfer.entity.Enum.Reason;
import lk.avsec_welfare.util.audit.AuditEntity;
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
@JsonFilter("Transfer")
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
