package lk.AVSEC.Welfare.asset.finance.service;

import lk.AVSEC.Welfare.asset.finance.dao.ReceivingFundDao;
import lk.AVSEC.Welfare.asset.finance.entity.ReceivingFund;
import lk.AVSEC.Welfare.util.interfaces.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReceivingFundService implements AbstractService<ReceivingFund, Integer> {

    private final ReceivingFundDao creditDao;

    @Autowired
    public ReceivingFundService(ReceivingFundDao creditDao) {
        this.creditDao = creditDao;
    }

    @Override
    public List<ReceivingFund> findAll() {
        return creditDao.findAll();
    }

    @Override
    public ReceivingFund findById(Integer id) {
        return creditDao.getOne(id);
    }

    @Override
    public ReceivingFund persist(ReceivingFund receivingFund) {
        return creditDao.save(receivingFund);
    }

    @Override
    public boolean delete(Integer id) {
        creditDao.deleteById(id);
        return true;
    }

    @Override
    public List<ReceivingFund> search(ReceivingFund receivingFund) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<ReceivingFund> creditExample = Example.of(receivingFund, matcher);
        return creditDao.findAll(creditExample);
    }
}
