package lk.AVSEC.Welfare.asset.ledger.dao;

import lk.AVSEC.Welfare.asset.ledger.entity.Ledger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LedgerDao extends JpaRepository<Ledger, Integer> {
}
