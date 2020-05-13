package lk.AVSEC.Welfare.asset.purchaseOrder.dao;

import lk.AVSEC.Welfare.asset.purchaseOrder.entity.PurchaseOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseOrderItemDao extends JpaRepository<PurchaseOrderItem, Integer> {
}
