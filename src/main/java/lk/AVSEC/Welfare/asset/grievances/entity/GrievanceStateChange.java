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

<<<<<<< HEAD
    private String comment;
=======
    private String remark;

    private String commentedBy;
>>>>>>> 991a9fa77174c9052d9120bd05ad1fd6ca31d6e1

    @ManyToOne
    private Grievance grievance;
}
