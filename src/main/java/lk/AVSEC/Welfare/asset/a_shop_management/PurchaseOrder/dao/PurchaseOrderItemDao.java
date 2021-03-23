package lk.AVSEC.Welfare.asset.a_shop_management.PurchaseOrder.dao;


import lk.AVSEC.Welfare.asset.a_shop_management.PurchaseOrder.entity.PurchaseOrder;
import lk.AVSEC.Welfare.asset.a_shop_management.PurchaseOrder.entity.PurchaseOrderItem;
import lk.AVSEC.Welfare.asset.a_shop_management.item.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface PurchaseOrderItemDao extends JpaRepository<PurchaseOrderItem, Integer> {
    PurchaseOrderItem findByPurchaseOrderAndItem(PurchaseOrder purchaseOrder, Item item);
    List<PurchaseOrderItem> findByPurchaseOrder(PurchaseOrder purchaseOrder);
}
