package lk.AVSEC.Welfare.asset.finance.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import lk.AVSEC.Welfare.asset.finance.entity.Enum.CollectionType;
import lk.AVSEC.Welfare.asset.finance.entity.Enum.Status;
import lk.AVSEC.Welfare.util.audit.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonFilter("FinancialYear")
public class FinancialYear extends AuditEntity {

    // @Size(min = 2, max = 60, message = "Your name length should be 13")
    private String financialYear;

    private String description;

    @Enumerated(EnumType.STRING)
    private Status status;

    private String remark;
}

