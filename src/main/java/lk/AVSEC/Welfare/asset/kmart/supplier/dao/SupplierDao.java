package lk.AVSEC.Welfare.asset.kmart.supplier.dao;

import lk.AVSEC.Welfare.asset.kmart.supplier.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierDao extends JpaRepository<Supplier, Integer> {
    Supplier findFirstByOrderByIdDesc();
}
