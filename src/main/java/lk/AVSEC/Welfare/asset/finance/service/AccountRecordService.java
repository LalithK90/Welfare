package lk.AVSEC.Welfare.asset.account.service;


import lk.AVSEC.Welfare.asset.finance.dao.AccountDao;
import lk.AVSEC.Welfare.asset.finance.entity.AccountRecord;
import lk.AVSEC.Welfare.util.interfaces.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
// spring transactional annotation need to tell spring to this method work through the project
@CacheConfig(cacheNames = "account")
public class AccountRecordService implements AbstractService<AccountRecord, Integer> {

    private final AccountDao accountDao;

    @Autowired
    public AccountRecordService(AccountDao accountDao) {

        this.accountDao = accountDao;
    }

    @Cacheable
    public List<AccountRecord> findAll() {

        return accountDao.findAll();
    }

    @Cacheable
    public AccountRecord findById(Integer id) {
        return accountDao.getOne(id);
    }

    @Caching(evict = {@CacheEvict(value = "account", allEntries = true)},
            put = {@CachePut(value = "account", key = "#accountRecord.id")})
    @Transactional
    public AccountRecord persist(AccountRecord accountRecord) {
        return accountDao.save(accountRecord);
    }

    @CacheEvict(allEntries = true)
    public boolean delete(Integer id) {
        accountDao.deleteById(id);
        return false;
    }

    @Cacheable
    public List<AccountRecord> search(AccountRecord accountRecord) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<AccountRecord> accountExample = Example.of(accountRecord, matcher);
        return accountDao.findAll(accountExample);
    }

    public boolean isAccountPresent(AccountRecord accountRecord) {
        return accountDao.equals(accountRecord);
    }


/*    public List<Account> findByProvince(Province province) {
        return accountDao.findByProvince(province);
    }*/
}
