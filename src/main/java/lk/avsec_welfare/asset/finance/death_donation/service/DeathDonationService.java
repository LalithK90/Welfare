package lk.avsec_welfare.asset.finance.death_donation.service;


import lk.avsec_welfare.asset.finance.death_donation.dao.DeathDonationDao;
import lk.avsec_welfare.asset.finance.death_donation.entity.DeathDonation;
import lk.avsec_welfare.util.interfaces.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeathDonationService implements AbstractService< DeathDonation, Integer> {

    private final DeathDonationDao deathDonationDao;

    @Autowired
    public DeathDonationService(DeathDonationDao deathDonationDao) {
        this.deathDonationDao = deathDonationDao;
    }

    public List< DeathDonation > findAll() {
        return deathDonationDao.findAll();
    }

    public DeathDonation findById(Integer id) {
        return deathDonationDao.getOne(id);
    }

    public DeathDonation persist(DeathDonation otherExpence) {
        return deathDonationDao.save(otherExpence);
    }

    public boolean delete(Integer id) {
        deathDonationDao.deleteById(id);
        return true;
    }

    public List< DeathDonation > search(DeathDonation otherExpence) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example< DeathDonation > debitExample = Example.of(otherExpence, matcher);
        return deathDonationDao.findAll(debitExample);
    }
}
