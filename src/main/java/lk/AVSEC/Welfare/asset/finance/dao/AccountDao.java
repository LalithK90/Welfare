package lk.AVSEC.Welfare.asset.finance.dao;

import lk.AVSEC.Welfare.asset.finance.entity.AccountRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountDao extends JpaRepository<AccountRecord, Integer> {

/*//select * from district where province = ?1
    List<Qualification> findByProvince(Province province);*/

}
