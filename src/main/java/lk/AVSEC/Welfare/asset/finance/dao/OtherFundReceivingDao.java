package lk.AVSEC.Welfare.asset.finance.dao;

import lk.AVSEC.Welfare.asset.finance.entity.Enum.OtherFundReceivingType;
import lk.AVSEC.Welfare.asset.finance.entity.MainAccount;
import lk.AVSEC.Welfare.asset.finance.entity.OtherFundReceiving;
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
public interface OtherFundReceivingDao extends JpaRepository<OtherFundReceiving, Integer> {


}
