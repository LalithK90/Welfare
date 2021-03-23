package lk.AVSEC.Welfare.asset.a_shop_management.branch.dao;


import lk.AVSEC.Welfare.asset.a_shop_management.branch.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BranchDao extends JpaRepository<Branch, Integer> {
}