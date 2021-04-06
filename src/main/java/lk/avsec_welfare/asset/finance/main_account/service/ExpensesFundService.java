package lk.avsec_welfare.asset.finance.main_account.service;

import lk.avsec_welfare.asset.common_asset.model.enums.LiveDead;
import lk.avsec_welfare.asset.finance.main_account.dao.ExpensesFundDao;
import lk.avsec_welfare.asset.finance.main_account.entity.ExpensesFund;
import lk.avsec_welfare.util.interfaces.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExpensesFundService implements AbstractService<ExpensesFund, Integer> {

    private final ExpensesFundDao expensesFundDao;

    @Autowired
    public ExpensesFundService(ExpensesFundDao expensesFundDao) {
        this.expensesFundDao = expensesFundDao;
    }

    public List<ExpensesFund> findAll() {
        return expensesFundDao.findAll().stream().filter(x->x.getLiveDead().equals(LiveDead.ACTIVE)).collect(Collectors.toList());
    }

    public ExpensesFund findById(Integer id) {
        return expensesFundDao.getOne(id);
    }

    public ExpensesFund persist(ExpensesFund expensesFund) {
        if ( expensesFund.getId() == null ){
            expensesFund.setLiveDead(LiveDead.ACTIVE);
        }
        return expensesFundDao.save(expensesFund);
    }

    public boolean delete(Integer id) {
        ExpensesFund expensesFund = expensesFundDao.getOne(id);
        expensesFund.setLiveDead(LiveDead.STOP);
        expensesFundDao.save(expensesFund);
        return true;
    }

    public List<ExpensesFund> search(ExpensesFund expensesFund) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<ExpensesFund> debitExample = Example.of(expensesFund, matcher);
        return expensesFundDao.findAll(debitExample);
    }
}
