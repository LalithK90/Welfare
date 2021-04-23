package lk.avsec_welfare.asset.finance.other_expence.service;


import lk.avsec_welfare.asset.finance.other_expence.dao.OtherExpenceDao;
import lk.avsec_welfare.asset.finance.other_expence.entity.OtherExpence;
import lk.avsec_welfare.util.interfaces.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OtherExpenceService implements AbstractService< OtherExpence, Integer> {

    private final OtherExpenceDao otherExpenceDao;

    @Autowired
    public OtherExpenceService(OtherExpenceDao otherExpenceDao) {
        this.otherExpenceDao = otherExpenceDao;
    }

    public List< OtherExpence > findAll() {
        return otherExpenceDao.findAll();
    }

    public OtherExpence findById(Integer id) {
        return otherExpenceDao.getOne(id);
    }

    public OtherExpence persist(OtherExpence otherExpence) {
        return otherExpenceDao.save(otherExpence);
    }

    public boolean delete(Integer id) {
        otherExpenceDao.deleteById(id);
        return true;
    }

    public List< OtherExpence > search(OtherExpence otherExpence) {
        ExampleMatcher matcher = ExampleMatcher
            .matching()
            .withIgnoreCase()
            .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example< OtherExpence > debitExample = Example.of(otherExpence, matcher);
        return otherExpenceDao.findAll(debitExample);
    }

    public List< OtherExpence > findByCreatedAtIsBetween(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return otherExpenceDao.findByCreatedAtIsBetween(startDateTime, endDateTime);
    }
}
