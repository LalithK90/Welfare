package lk.AVSEC.Welfare.asset.finance.entity;

import lk.AVSEC.Welfare.asset.finance.entity.Enum.CollectionType;
import lk.AVSEC.Welfare.util.audit.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InstalmentType extends AuditEntity {

    @Column(nullable = false)
    private String year;

    private String remark;

    @Enumerated(EnumType.STRING)
    private CollectionType collectionType;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @OneToMany(mappedBy = "instalmentType")
    private List<Instalment> instalments;

}
