package lk.AVSEC.Welfare.asset.PurchaseOrder.dao;


import lk.AVSEC.Welfare.asset.PurchaseOrder.entity.PurchaseOrder;
import lk.AVSEC.Welfare.asset.PurchaseOrder.entity.PurchaseOrderItem;
import lk.AVSEC.Welfare.asset.item.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface PurchaseOrderItemDao extends JpaRepository<PurchaseOrderItem, Integer> {
    PurchaseOrderItem findByPurchaseOrderAndItem(PurchaseOrder purchaseOrder, Item item);
    List<PurchaseOrderItem> findByPurchaseOrder(PurchaseOrder purchaseOrder);
}
