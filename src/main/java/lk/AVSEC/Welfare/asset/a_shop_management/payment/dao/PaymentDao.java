package lk.AVSEC.Welfare.asset.a_shop_management.payment.dao;


import lk.AVSEC.Welfare.asset.a_shop_management.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentDao extends JpaRepository<Payment,Integer> {
    List< Payment> findByPurchaseOrder(lk.AVSEC.Welfare.asset.a_shop_management.PurchaseOrder.entity.PurchaseOrder purchaseOrder);

    Payment findFirstByOrderByIdDesc();
}
