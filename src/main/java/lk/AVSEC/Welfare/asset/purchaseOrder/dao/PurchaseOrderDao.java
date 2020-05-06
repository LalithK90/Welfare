package lk.AVSEC.Welfare.asset.purchaseOrder.dao;

import lk.AVSEC.Welfare.asset.purchaseOrder.entity.Enum.PurchaseOrderStatus;
import lk.AVSEC.Welfare.asset.purchaseOrder.entity.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseOrderDao extends JpaRepository<PurchaseOrder, Integer> {
    List<PurchaseOrder> findByPurchaseOrderStatus(PurchaseOrderStatus purchaseOrderStatus);

    PurchaseOrder findFirstByOrderByIdDesc();
}
