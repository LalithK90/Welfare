package lk.AVSEC.Welfare.asset.item.dao;


import lk.AVSEC.Welfare.asset.category.entity.Category;
import lk.AVSEC.Welfare.asset.item.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemDao extends JpaRepository<Item, Integer> {
    Item findFirstByOrderByIdDesc();

    List<Item> findByCategoryOrderByIdDesc(Category category);
}
