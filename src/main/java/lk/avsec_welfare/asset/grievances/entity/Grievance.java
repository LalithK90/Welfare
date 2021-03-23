package lk.avsec_welfare.asset.grievances.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import lk.avsec_welfare.asset.common_asset.model.enums.LiveDead;
import lk.avsec_welfare.asset.grievances.entity.enums.GrievancesStatus;
import lk.avsec_welfare.asset.grievances.entity.enums.Priority;
import lk.avsec_welfare.asset.grievances.entity.enums.SolutionType;
import lk.avsec_welfare.util.audit.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonFilter("Grievance")
public class Grievance extends AuditEntity {


    @Enumerated( EnumType.STRING )
    private LiveDead liveDead;

    private String description;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    @Enumerated(EnumType.STRING)
    private GrievancesStatus grievancesStatus;

    @Enumerated(EnumType.STRING)
    private SolutionType solutionType;

    @OneToMany(mappedBy = "grievance", cascade = {CascadeType.PERSIST,CascadeType.DETACH})
    private List<GrievanceStateChange> grievanceStateChange;

    @Transient
    private String remark;

}
