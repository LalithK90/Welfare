package lk.avsec_welfare.asset.grievances.dao;

import lk.avsec_welfare.asset.grievances.entity.enums.GrievancesStatus;
import lk.avsec_welfare.asset.grievances.entity.enums.SolutionType;
import lk.avsec_welfare.asset.grievances.entity.Grievance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface GrievancesDao extends JpaRepository<Grievance, Integer> {

/*//select * from district where province = ?1
    List<Qualification> findByProvince(Province province);*/

    //solution type, date range, grievance Status
    List<Grievance> findBySolutionTypeAndGrievancesStatusAndCreatedAtBetween(SolutionType solutionType, GrievancesStatus grievancesStatus, LocalDateTime form, LocalDateTime to);

    List<Grievance> findBySolutionTypeAndCreatedByAndCreatedAtBetween(SolutionType solutionType, String userName, LocalDateTime form, LocalDateTime to);

  List< Grievance> findByCreatedBy(String userName);

}
