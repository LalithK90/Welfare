package lk.AVSEC.Welfare.asset.kmart.customer.dao;

import lk.AVSEC.Welfare.asset.kmart.customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerDao extends JpaRepository<Customer, Integer> {
    Customer findFirstByOrderByIdDesc();
}
