package lk.avsec_welfare.asset.designation.dao;

import lk.avsec_welfare.asset.designation.entity.Designation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DesignationDao extends JpaRepository< Designation, Integer> {


}
