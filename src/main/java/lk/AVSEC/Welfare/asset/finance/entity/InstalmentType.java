package lk.AVSEC.Welfare.asset.finance.entity;

import lk.AVSEC.Welfare.asset.finance.entity.Enum.CollectionType;
<<<<<<< HEAD
=======
import lk.AVSEC.Welfare.asset.finance.entity.Enum.ExpenseOrReceived;
>>>>>>> 991a9fa77174c9052d9120bd05ad1fd6ca31d6e1
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

<<<<<<< HEAD
    @Column(nullable = false)
    private String year;

    private String remark;
=======
    private String name;

    private String year;

    @Enumerated(EnumType.STRING)
    private ExpenseOrReceived expenseOrReceived;
>>>>>>> 991a9fa77174c9052d9120bd05ad1fd6ca31d6e1

    @Enumerated(EnumType.STRING)
    private CollectionType collectionType;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @OneToMany(mappedBy = "instalmentType")
    private List<Instalment> instalments;

}
