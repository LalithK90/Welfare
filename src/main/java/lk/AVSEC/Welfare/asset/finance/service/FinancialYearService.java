package lk.AVSEC.Welfare.asset.finance.service;

import lk.AVSEC.Welfare.asset.finance.dao.FinancialYearDao;
import lk.AVSEC.Welfare.asset.finance.entity.FinancialYear;
import lk.AVSEC.Welfare.util.interfaces.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Service
// spring transactional annotation need to tell spring to this method work through the project
@CacheConfig(cacheNames = "financialYear")
public class FinancialYearService implements AbstractService<FinancialYear, Integer> {

    private final FinancialYearDao financialYearDao;

    @Autowired
    public FinancialYearService(FinancialYearDao financialYearDao) {

        this.financialYearDao = financialYearDao;
    }

    @Override
    public List<FinancialYear> findAll() {
        return financialYearDao.findAll();
    }

    @Override
    public FinancialYear findById(Integer id) {
        return financialYearDao.getOne(id);
    }

    @Override
    public FinancialYear persist(FinancialYear financialYear) {
        return financialYearDao.save(financialYear);
    }

    @Override
    public boolean delete(Integer id) {
        financialYearDao.deleteById(id);
        return false;
    }

    @Override
    public List<FinancialYear> search(FinancialYear financialYear) {
        return null;
    }
}
