package lk.avsec_welfare.asset.designation.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import lk.avsec_welfare.asset.designation.entity.Enum.CategoryType;
import lk.avsec_welfare.asset.designation.entity.Enum.SalaryScale;
import lk.avsec_welfare.util.audit.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonFilter("Designation")
public class Designation extends AuditEntity {


    private String name;

    private String shortName;

    @Enumerated (EnumType.STRING)
    private CategoryType categoryType;

    @Enumerated (EnumType.STRING)
    private SalaryScale salaryScale;

    private String remark;

}
