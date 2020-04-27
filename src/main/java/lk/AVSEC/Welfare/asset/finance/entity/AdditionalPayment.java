package lk.AVSEC.Welfare.asset.finance.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import lk.AVSEC.Welfare.asset.employee.entity.Employee;
import lk.AVSEC.Welfare.asset.finance.entity.Enum.CollectionType;
import lk.AVSEC.Welfare.asset.finance.entity.Enum.ReceivingExpensesStatus;
import lk.AVSEC.Welfare.util.audit.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Year;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonFilter("AdditionalPayment")
public class AdditionalPayment extends AuditEntity {

    // @Size(min = 2, max = 60, message = "Your name length should be 13")
    private String financialYear;

    private String amount;

    private String reason;

    @Enumerated(EnumType.STRING)
    private CollectionType collectionType;

    private Date dateWEF;
}

