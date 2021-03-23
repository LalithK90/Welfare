package lk.AVSEC.Welfare.asset.a_shop_management.payment.service;

import lk.AVSEC.Welfare.asset.a_shop_management.payment.dao.PaymentDao;
import lk.AVSEC.Welfare.asset.a_shop_management.payment.entity.Payment;
import lk.AVSEC.Welfare.util.interfaces.AbstractService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService implements AbstractService<Payment, Integer > {
    private final PaymentDao paymentDao;

    public PaymentService(PaymentDao paymentDao) {
        this.paymentDao = paymentDao;
    }

    public List< Payment > findAll() {
        return paymentDao.findAll();
    }

    public Payment findById(Integer id) {
        return paymentDao.getOne(id);
    }

    public Payment persist(Payment payment) {
        return paymentDao.save(payment);
    }

    public boolean delete(Integer id) {
        return false;
    }

    public List< Payment > search(Payment payment) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example< Payment > paymentExample = Example.of(payment, matcher);
        return paymentDao.findAll(paymentExample);
    }

    public List< Payment > findByPurchaseOrder(lk.AVSEC.Welfare.asset.a_shop_management.PurchaseOrder.entity.PurchaseOrder purchaseOrder) {
        return paymentDao.findByPurchaseOrder(purchaseOrder);
    }

    public Payment lastPayment() {
        return paymentDao.findFirstByOrderByIdDesc();
    }
}
