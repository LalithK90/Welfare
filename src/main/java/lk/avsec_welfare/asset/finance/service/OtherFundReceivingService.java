package lk.avsec_welfare.asset.finance.service;


import lk.avsec_welfare.asset.finance.dao.OtherFundReceivingDao;
import lk.avsec_welfare.asset.finance.entity.OtherFundReceiving;
import lk.avsec_welfare.util.interfaces.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OtherFundReceivingService implements AbstractService<OtherFundReceiving, Integer> {

    private final OtherFundReceivingDao otherFundReceivingDao;

    @Autowired
    public OtherFundReceivingService(OtherFundReceivingDao otherFundReceivingDao) {
        this.otherFundReceivingDao = otherFundReceivingDao;
    }

    public List<OtherFundReceiving> findAll() {
        return otherFundReceivingDao.findAll();
    }

    public OtherFundReceiving findById(Integer id) {
        return otherFundReceivingDao.getOne(id);
    }

    public OtherFundReceiving persist(OtherFundReceiving otherFundReceiving) {
        return otherFundReceivingDao.save(otherFundReceiving);
    }

    public boolean delete(Integer id) {
        otherFundReceivingDao.deleteById(id);
        return true;
    }

    public List<OtherFundReceiving> search(OtherFundReceiving otherFundReceiving) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<OtherFundReceiving> debitExample = Example.of(otherFundReceiving, matcher);
        return otherFundReceivingDao.findAll(debitExample);
    }
}