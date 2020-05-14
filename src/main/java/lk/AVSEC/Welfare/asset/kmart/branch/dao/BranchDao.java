package lk.AVSEC.Welfare.asset.kmart.branch.dao;

import lk.AVSEC.Welfare.asset.kmart.branch.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BranchDao extends JpaRepository<Branch, Integer> {
}
