package lk.AVSEC.Welfare.asset.finance.service;

import lk.AVSEC.Welfare.asset.finance.dao.AdditionalPaymentDao;
import lk.AVSEC.Welfare.asset.finance.entity.AdditionalPayment;
import lk.AVSEC.Welfare.util.interfaces.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
// spring transactional annotation need to tell spring to this method work through the project
@CacheConfig(cacheNames = "additionalPayment")
public class AdditionalPaymentService implements AbstractService<AdditionalPayment, Integer> {

    private final AdditionalPaymentDao additionalPaymentDao;

    @Autowired
    public AdditionalPaymentService(AdditionalPaymentDao additionalPaymentDao) {

        this.additionalPaymentDao = additionalPaymentDao;
    }

    @Override
    public List<AdditionalPayment> findAll() {
        return additionalPaymentDao.findAll();
    }

    @Override
    public AdditionalPayment findById(Integer id) {
        return additionalPaymentDao.getOne(id);
    }

    @Override
    public AdditionalPayment persist(AdditionalPayment additionalPayment) {
        return additionalPaymentDao.save(additionalPayment);
    }

    @Override
    public boolean delete(Integer id) {
        additionalPaymentDao.deleteById(id);
        return false;
    }

    @Override
    public List<AdditionalPayment> search(AdditionalPayment additionalPayment) {
        return null;
    }
}
