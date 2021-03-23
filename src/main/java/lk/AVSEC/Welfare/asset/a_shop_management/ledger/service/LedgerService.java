package lk.AVSEC.Welfare.asset.a_shop_management.ledger.service;


import lk.AVSEC.Welfare.asset.a_shop_management.item.entity.Item;
import lk.AVSEC.Welfare.asset.a_shop_management.ledger.dao.LedgerDao;
import lk.AVSEC.Welfare.asset.a_shop_management.ledger.entity.Ledger;
import lk.AVSEC.Welfare.util.interfaces.AbstractService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@CacheConfig(cacheNames = "ledger")
public class LedgerService implements AbstractService<Ledger, Integer> {
    private final LedgerDao ledgerDao;

    public LedgerService(LedgerDao ledgerDao) {
        this.ledgerDao = ledgerDao;
    }


    public List<Ledger> findAll() {
        return ledgerDao.findAll();
    }


    public Ledger findById(Integer id) {
        return ledgerDao.getOne(id);
    }


    public Ledger persist(Ledger ledger) {
        return ledgerDao.save(ledger);
    }


    public boolean delete(Integer id) {
        //not applicable
        return false;
    }


    public List<Ledger> search(Ledger ledger) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Ledger> ledgerExample = Example.of(ledger, matcher);
        return ledgerDao.findAll(ledgerExample);
    }

    public List<Ledger> findByItem(Item item) {
        return ledgerDao.findByItem(item);
    }

    public Ledger findByItemAndAndExpiredDateAndSellPrice(Item item, LocalDate eDate, BigDecimal sellPrice) {
    return ledgerDao.findByItemAndAndExpiredDateAndSellPrice( item, eDate, sellPrice);
    }

    public List<Ledger> findByCreatedAtIsBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return ledgerDao.findByCreatedAtIsBetween(startDate, endDate);
    }

   /* public Ledger findByItem(Item item) {
        return ledgerDao.findByItem(item);
    }*/
}
