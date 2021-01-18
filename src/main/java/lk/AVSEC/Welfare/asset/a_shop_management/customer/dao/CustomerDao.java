package lk.AVSEC.Welfare.asset.a_shop_management.customer.dao;


import lk.AVSEC.Welfare.asset.a_shop_management.customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerDao extends JpaRepository<Customer, Integer> {
    Customer findFirstByOrderByIdDesc();
}
