package lk.AVSEC.Welfare.asset.finance.service;

import lk.AVSEC.Welfare.asset.finance.dao.AddPaymentTypeDao;
import lk.AVSEC.Welfare.asset.finance.entity.AddPaymentType;
import lk.AVSEC.Welfare.util.interfaces.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
// spring transactional annotation need to tell spring to this method work through the project
@CacheConfig(cacheNames = "addPaymentType")
public class AddPaymentTypeService implements AbstractService<AddPaymentType, Integer> {

    private final AddPaymentTypeDao addPaymentTypeDao;

    @Autowired
    public AddPaymentTypeService(AddPaymentTypeDao addPaymentTypeDao) {

        this.addPaymentTypeDao = addPaymentTypeDao;
    }

    @Override
    public List<AddPaymentType> findAll() {
        return addPaymentTypeDao.findAll();
    }

    @Override
    public AddPaymentType findById(Integer id) {
        return addPaymentTypeDao.getOne(id);
    }



    @Override
    public AddPaymentType persist(AddPaymentType addPaymentType) {
        return addPaymentTypeDao.save(addPaymentType);
    }

    @Override
    public boolean delete(Integer id) {
        addPaymentTypeDao.deleteById(id);
        return false;
    }

    @Override
    public List<AddPaymentType> search(AddPaymentType addPaymentType) {
        return null;
    }


}
