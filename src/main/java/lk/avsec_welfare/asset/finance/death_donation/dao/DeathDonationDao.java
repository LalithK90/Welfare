package lk.avsec_welfare.asset.finance.death_donation.dao;

import lk.avsec_welfare.asset.finance.death_donation.entity.DeathDonation;
import lk.avsec_welfare.asset.finance.other_expence.entity.OtherExpence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeathDonationDao extends JpaRepository< DeathDonation, Integer> {


}
