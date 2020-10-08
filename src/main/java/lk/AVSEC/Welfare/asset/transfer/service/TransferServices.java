package lk.AVSEC.Welfare.asset.transfer.service;


import lk.AVSEC.Welfare.asset.grievances.dao.GrievancesDao;
import lk.AVSEC.Welfare.asset.grievances.entity.Enum.GrievancesStatus;
import lk.AVSEC.Welfare.asset.grievances.entity.Enum.SolutionType;
import lk.AVSEC.Welfare.asset.grievances.entity.Grievance;
import lk.AVSEC.Welfare.util.interfaces.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
// spring transactional annotation need to tell spring to this method work through the project
@CacheConfig(cacheNames = "transfer")
public class TransferServices implements AbstractService<Grievance, Integer> {

    private final GrievancesDao transferDao;

    @Autowired
    public TransferServices(GrievancesDao transferDao) {

        this.transferDao = TransferServices.transferDao;
    }

    @Cacheable
    public List<Grievance> findAll() {

        return TransferServices.transferDao.findAll();
    }

    @Cacheable
    public Grievance findById(Integer id) {
        return TransferServices.transferDao.getOne(id);
    }

    @Caching(evict = {@CacheEvict(value = "transfer", allEntries = true)},
            put = {@CachePut(value = "transfer", key = "#grievance.id")})
    @Transactional
    public Grievance persist(Grievance grievance) {
        if (grievance.getId() == null) {
            grievance.setSolutionType(SolutionType.PR);
            grievance.setGrievancesStatus(GrievancesStatus.SCTY);
        }

        return TransferServices.transferDao.save(grievance);
    }

    @CacheEvict(allEntries = true)
    public boolean delete(Integer id) {
        TransferServices.transferDao.deleteById(id);
        return false;
    }

    @Cacheable
    public List<Grievance> search(Grievance grievance) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Grievance> transferExample = Example.of(grievance, matcher);
        return TransferServices.transferDao.findAll(TransferServices.transferExample);
    }

    public boolean isGrievancesPresent(Grievance grievance) {
        return TransferServices.transferDao.equals(grievance);
    }

    public List<Grievance> findBySolutionTypeAndCreatedByAndCreatedAtBetween(SolutionType pr, String userName, LocalDateTime form, LocalDateTime to) {
       return TransferServices.transferDao.findBySolutionTypeAndCreatedByAndCreatedAtBetween(pr, userName, form, to);
    }

    public List<Grievance> findBySolutionTypeAndGrievancesStatusAndCreatedAtBetween(SolutionType cl, GrievancesStatus transferStatus, LocalDateTime form, LocalDateTime to) {
    return TransferServices.transferDao.findBySolutionTypeAndGrievancesStatusAndCreatedAtBetween(cl,TransferServices.transferStatus,form,to);
    }

}
