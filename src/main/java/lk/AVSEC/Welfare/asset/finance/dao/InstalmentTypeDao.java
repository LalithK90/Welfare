package lk.AVSEC.Welfare.asset.finance.dao;

import lk.AVSEC.Welfare.asset.finance.entity.Enum.CollectionType;
import lk.AVSEC.Welfare.asset.finance.entity.Instalment;
import lk.AVSEC.Welfare.asset.finance.entity.InstalmentType;
import lk.AVSEC.Welfare.util.audit.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Repository
public interface InstalmentTypeDao extends JpaRepository<InstalmentType, Integer> {


}
