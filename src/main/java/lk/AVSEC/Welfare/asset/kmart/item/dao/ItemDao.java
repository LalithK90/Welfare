package lk.AVSEC.Welfare.asset.kmart.item.dao;


import lk.AVSEC.Welfare.asset.kmart.item.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemDao extends JpaRepository<Item, Integer> {
    Item findFirstByOrderByIdDesc();
}
