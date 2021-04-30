package lk.avsec_welfare.asset.course.entity;


import com.fasterxml.jackson.annotation.JsonFilter;
import lk.avsec_welfare.asset.common_asset.model.enums.LiveDead;
import lk.avsec_welfare.asset.employee.entity.Employee;
import lk.avsec_welfare.asset.qualification.entity.Qualification;
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
@JsonFilter("Course")
public class Course extends AuditEntity {

    private String name;

    private String institute;

    private String remark;


    @Enumerated(EnumType.STRING)
    private LiveDead liveDead;

    @OneToMany(mappedBy = "course")
    private List<Qualification>qualifications;

}
