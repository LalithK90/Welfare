package lk.avsec_welfare.asset.userManagement.dao;


import lk.avsec_welfare.asset.userManagement.entity.ConformationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConformationTokenRepository extends JpaRepository< ConformationToken, Integer> {
    ConformationToken findByToken(String token);
}
