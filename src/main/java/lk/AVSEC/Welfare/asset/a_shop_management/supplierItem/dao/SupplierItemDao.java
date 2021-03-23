package lk.AVSEC.Welfare.asset.a_shop_management.supplierItem.dao;


import lk.AVSEC.Welfare.asset.a_shop_management.item.entity.Item;
import lk.AVSEC.Welfare.asset.a_shop_management.supplier.entity.Supplier;
import lk.AVSEC.Welfare.asset.a_shop_management.supplierItem.entity.Enum.ItemSupplierStatus;
import lk.AVSEC.Welfare.asset.a_shop_management.supplierItem.entity.SupplierItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierItemDao extends JpaRepository<SupplierItem, Integer> {
    SupplierItem findBySupplierAndItem(Supplier supplier, Item item);

    List<SupplierItem> findBySupplier(Supplier supplier);

    List<SupplierItem> findBySupplierAndItemSupplierStatus(Supplier supplier, ItemSupplierStatus itemSupplierStatus);

    List<SupplierItem> findByItem(Item item);

    Item findByItemAndSupplier(Item item, Supplier supplier);

    SupplierItem findBySupplierAndItemAndItemSupplierStatus(Supplier supplier, Item item, ItemSupplierStatus itemSupplierStatus);
}
