package lk.AVSEC.Welfare.asset.a_shop_management.goodReceivedNote.dao;


import lk.AVSEC.Welfare.asset.a_shop_management.goodReceivedNote.entity.GoodReceivedNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoodReceivedNoteDao extends JpaRepository<GoodReceivedNote, Integer> {
    GoodReceivedNote findByPurchaseOrder(lk.AVSEC.Welfare.asset.a_shop_management.PurchaseOrder.entity.PurchaseOrder purchaseOrder);
}
