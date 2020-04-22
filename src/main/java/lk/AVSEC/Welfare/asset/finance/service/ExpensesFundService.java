package lk.AVSEC.Welfare.asset.finance.service;

import lk.AVSEC.Welfare.asset.finance.dao.ExpensesFundDao;
import lk.AVSEC.Welfare.asset.finance.entity.ExpensesFund;
import lk.AVSEC.Welfare.util.interfaces.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpensesFundService implements AbstractService<ExpensesFund, Integer> {

    private final ExpensesFundDao expensesFundDao;

    @Autowired
    public ExpensesFundService(ExpensesFundDao expensesFundDao) {
        this.expensesFundDao = expensesFundDao;
    }

    @Override
    public List<ExpensesFund> findAll() {
        return expensesFundDao.findAll();
    }

    @Override
    public ExpensesFund findById(Integer id) {
        return expensesFundDao.getOne(id);
    }

    @Override
    public ExpensesFund persist(ExpensesFund expensesFund) {
        return expensesFundDao.save(expensesFund);
    }

    @Override
    public boolean delete(Integer id) {
        expensesFundDao.deleteById(id);
        return true;
    }

    @Override
    public List<ExpensesFund> search(ExpensesFund expensesFund) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<ExpensesFund> debitExample = Example.of(expensesFund, matcher);
        return expensesFundDao.findAll(debitExample);
    }
}
