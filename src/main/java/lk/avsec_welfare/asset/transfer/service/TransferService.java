package lk.avsec_welfare.asset.transfer.service;


import lk.avsec_welfare.asset.transfer.dao.TransferDao;
import lk.avsec_welfare.asset.transfer.entity.Transfer;
import lk.avsec_welfare.util.interfaces.AbstractService;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
// spring transactional annotation need to tell spring to this method work through the project
@CacheConfig( cacheNames = "transfer" )
public class TransferService implements AbstractService< Transfer, Integer > {

    private final TransferDao transferDao;

    public TransferService(TransferDao transferDao) {
        this.transferDao = transferDao;
    }


    @Cacheable
    public List< Transfer > findAll() {

        return transferDao.findAll();
    }

    @Cacheable
    public Transfer findById(Integer id) {
        return transferDao.getOne(id);
    }

    @Caching( evict = {@CacheEvict( value = "transfer", allEntries = true )},
            put = {@CachePut( value = "transfer", key = "#transfer.id" )} )
    @Transactional
    public Transfer persist(Transfer transfer) {
//        if ( transfer.getId() == null ) {
//            transfer.setSolutionType(SolutionType.PR);
//            transfer.setTransfersStatus(TransfersStatus.SCTY);
//        }

        return transferDao.save(transfer);
    }

    @CacheEvict( allEntries = true )
    public boolean delete(Integer id) {
        transferDao.deleteById(id);
        return false;
    }

    @Cacheable
    public List< Transfer > search(Transfer transfer) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example< Transfer > transferExample = Example.of(transfer, matcher);
        return transferDao.findAll(transferExample);
    }

    public boolean isTransfersPresent(Transfer transfer) {
        return transferDao.equals(transfer);
    }

//    public List< Transfer > findBySolutionTypeAndCreatedByAndCreatedAtBetween(SolutionType pr, String userName,
//                                                                              LocalDateTime form, LocalDateTime to) {
//        return transferDao.findBySolutionTypeAndCreatedByAndCreatedAtBetween(pr, userName, form, to);
//    }
//
//    public List< Transfer > findBySolutionTypeAndTransfersStatusAndCreatedAtBetween(SolutionType cl,
//                                                                                    TransfersStatus transferStatus,
//                                                                                    LocalDateTime form,
//                                                                                    LocalDateTime to) {
//        return transferDao.findBySolutionTypeAndTransfersStatusAndCreatedAtBetween(cl, transferStatus, form, to);
//    }

}
