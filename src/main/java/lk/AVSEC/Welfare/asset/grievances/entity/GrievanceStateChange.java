package lk.AVSEC.Welfare.asset.grievances.entity;

import lk.AVSEC.Welfare.asset.grievances.entity.Enum.Priority;
import lk.AVSEC.Welfare.util.audit.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GrievanceStateChange extends AuditEntity {

    private String remark;

    private String commentedBy;

    @ManyToOne
    private Grievance grievance;
}
