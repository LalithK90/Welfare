package lk.avsec_welfare.asset.death_donation.dao;

import lk.avsec_welfare.asset.death_donation.entity.DeathDonation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DeathDonationDao extends JpaRepository< DeathDonation, Integer> {


  List< DeathDonation > findByCreatedAtIsBetween(LocalDateTime startDateTime, LocalDateTime endDateTime);
}
