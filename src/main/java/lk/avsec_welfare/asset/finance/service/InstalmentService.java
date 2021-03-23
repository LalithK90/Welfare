package lk.avsec_welfare.asset.finance.service;

import lk.avsec_welfare.asset.employee.entity.Employee;
import lk.avsec_welfare.asset.finance.dao.InstalmentDao;
import lk.avsec_welfare.asset.finance.entity.Instalment;
import lk.avsec_welfare.util.interfaces.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstalmentService implements AbstractService<Instalment, Integer> {

    private final InstalmentDao instalmentDao;

    @Autowired
    public InstalmentService(InstalmentDao instalmentDao) {
        this.instalmentDao = instalmentDao;
    }

    public List<Instalment> findAll() {
        return instalmentDao.findAll();
    }

    public Instalment findById(Integer id) {
        return instalmentDao.getOne(id);
    }

    public Instalment persist(Instalment instalment) {
        return instalmentDao.save(instalment);
    }

    public boolean delete(Integer id) {
        instalmentDao.deleteById(id);
        return true;
    }

    public List<Instalment> search(Instalment instalment) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Instalment> debitExample = Example.of(instalment, matcher);
        return instalmentDao.findAll(debitExample);
    }

    public List<Instalment> findByEmployee(Employee employee) {
    return instalmentDao.findByEmployee(employee);
    }
}
