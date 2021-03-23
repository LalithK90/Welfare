package lk.avsec_welfare.asset.district.service;

import lk.avsec_welfare.asset.common_asset.model.enums.LiveDead;
import lk.avsec_welfare.asset.common_asset.model.enums.Province;
import lk.avsec_welfare.asset.district.dao.DistrictDao;
import lk.avsec_welfare.asset.district.entity.District;
import lk.avsec_welfare.util.interfaces.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
// spring transactional annotation need to tell spring to this method work through the project
@CacheConfig(cacheNames = "district")
public class DistrictService implements AbstractService< District, Integer> {
    private final DistrictDao districtDao;

    @Autowired
    public DistrictService(DistrictDao districtDao) {

        this.districtDao = districtDao;
    }

    @Cacheable
    public List<District> findAll() {
        return districtDao.findAll().stream().filter(x->x.getLiveDead().equals(LiveDead.ACTIVE)).collect(Collectors.toList());
    }

    @Cacheable
    public District findById(Integer id) {
        return districtDao.getOne(id);
    }

    @Caching(evict = {@CacheEvict(value = "district", allEntries = true)},
            put = {@CachePut(value = "district", key = "#district.id")})
    @Transactional
    public District persist(District district) {
        if ( district.getId() == null ){
            district.setLiveDead(LiveDead.ACTIVE);
        }
        return districtDao.save(district);
    }

    @CacheEvict(allEntries = true)
    public boolean delete(Integer id) {
        District district = districtDao.getOne(id);
        district.setLiveDead(LiveDead.ACTIVE);
        districtDao.save(district);
        return false;
    }

    @Cacheable
    public List<District> search(District district) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<District> districtExample = Example.of(district, matcher);
        return districtDao.findAll(districtExample);
    }

    public boolean isDistrictPresent(District district) {
        return districtDao.equals(district);
    }


    public List<District> findByProvince(Province province) {
        return districtDao.findByProvince(province);
    }
}
