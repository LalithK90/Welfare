package lk.AVSEC.Welfare.asset.grievances.dao;

<<<<<<< HEAD
=======
import lk.AVSEC.Welfare.asset.grievances.entity.Enum.GrievancesStatus;
import lk.AVSEC.Welfare.asset.grievances.entity.Enum.SolutionType;
>>>>>>> 991a9fa77174c9052d9120bd05ad1fd6ca31d6e1
import lk.AVSEC.Welfare.asset.grievances.entity.Grievance;
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
}
