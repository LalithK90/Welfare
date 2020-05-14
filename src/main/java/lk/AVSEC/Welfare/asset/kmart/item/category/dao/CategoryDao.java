package lk.AVSEC.Welfare.asset.kmart.item.category.dao;

import lk.AVSEC.Welfare.asset.kmart.item.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryDao extends JpaRepository<Category, Integer> {
}
