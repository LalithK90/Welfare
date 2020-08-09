package lk.AVSEC.Welfare.asset.designation.dao;

import lk.AVSEC.Welfare.asset.designation.entity.Designation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DesignationDao extends JpaRepository<Designation, Integer> {


}
