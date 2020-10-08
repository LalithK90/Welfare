package lk.AVSEC.Welfare.asset.transfer.dao;

import lk.AVSEC.Welfare.asset.grievances.entity.Enum.SolutionType;
import lk.AVSEC.Welfare.asset.transfer.entity.Enum.Reason;

import lk.AVSEC.Welfare.asset.transfer.entity.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransferDao extends JpaRepository<Transfer, Integer> {

}
