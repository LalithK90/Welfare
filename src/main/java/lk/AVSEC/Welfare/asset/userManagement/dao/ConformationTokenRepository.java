package lk.AVSEC.Welfare.asset.userManagement.dao;


import lk.AVSEC.Welfare.asset.userManagement.entity.ConformationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConformationTokenRepository extends JpaRepository<ConformationToken, Integer> {
    ConformationToken findByToken(String token);
}
