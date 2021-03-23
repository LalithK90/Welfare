package lk.avsec_welfare.asset.dependent.dao;

import lk.avsec_welfare.asset.dependent.entity.Dependent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DependentDao extends JpaRepository< Dependent, Integer> {
    Dependent findByNic(String nic);
}
