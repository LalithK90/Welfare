package lk.avsec_welfare.asset.transfer.dao;



import lk.avsec_welfare.asset.transfer.entity.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TransferDao extends JpaRepository< Transfer, Integer> {

}
