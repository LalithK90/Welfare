package lk.AVSEC.Welfare.asset.district.service;

import lk.AVSEC.Welfare.asset.commonAsset.model.Enum.Province;
import lk.AVSEC.Welfare.asset.district.dao.DistrictDao;
import lk.AVSEC.Welfare.asset.district.entity.District;
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
@CacheConfig(cacheNames = "district")
public class DistrictService implements AbstractService<District, Integer> {

    private final DistrictDao districtDao;

    @Autowired
    public DistrictService(DistrictDao districtDao) {
        this.districtDao = districtDao;
    }

    @Cacheable
    public List<District> findAll() {
        return districtDao.findAll();
    }

    @Cacheable
    public District findById(Integer id) {
        return districtDao.getOne(id);
    }

    @Caching(evict = {@CacheEvict(value = "district", allEntries = true)},
            put = {@CachePut(value = "district", key = "#district.id")})
    @Transactional
    public District persist(District district) {
        return districtDao.save(district);
    }

    @CacheEvict(allEntries = true)
    public boolean delete(Integer id) {
        districtDao.deleteById(id);
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
