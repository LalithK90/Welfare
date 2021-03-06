package lk.AVSEC.Welfare.asset.supplier.dao;


import lk.AVSEC.Welfare.asset.supplier.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierDao extends JpaRepository<Supplier, Integer> {
    Supplier findFirstByOrderByIdDesc();

    Supplier findByIdAndItemSupplierStatus(Integer supplierId, lk.AVSEC.Welfare.asset.supplierItem.entity.Enum.ItemSupplierStatus itemSupplierStatus);
}
