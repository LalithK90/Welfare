package lk.avsec_welfare.asset.grievances.entity;

import lk.avsec_welfare.util.audit.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
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
