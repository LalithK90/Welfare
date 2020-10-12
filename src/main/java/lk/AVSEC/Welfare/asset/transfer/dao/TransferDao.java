package lk.AVSEC.Welfare.asset.transfer.dao;



import lk.AVSEC.Welfare.asset.transfer.entity.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TransferDao extends JpaRepository<Transfer, Integer> {

}
