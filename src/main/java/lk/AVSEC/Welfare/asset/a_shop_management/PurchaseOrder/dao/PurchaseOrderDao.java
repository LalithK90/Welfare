package lk.AVSEC.Welfare.asset.a_shop_management.PurchaseOrder.dao;


import lk.AVSEC.Welfare.asset.a_shop_management.PurchaseOrder.entity.Enum.PurchaseOrderStatus;
import lk.AVSEC.Welfare.asset.a_shop_management.PurchaseOrder.entity.PurchaseOrder;
import lk.AVSEC.Welfare.asset.a_shop_management.supplier.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseOrderDao extends JpaRepository<PurchaseOrder, Integer> {

    List<PurchaseOrder> findByPurchaseOrderStatus(PurchaseOrderStatus purchaseOrderStatus);

    List<PurchaseOrder> findByPurchaseOrderStatusAndSupplier(PurchaseOrderStatus purchaseOrderStatus, Supplier supplier);


    PurchaseOrder findFirstByOrderByIdDesc();
}
