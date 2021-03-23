package lk.avsec_welfare.asset.userManagement.service;

import lk.avsec_welfare.asset.common_asset.model.enums.LiveDead;
import lk.avsec_welfare.asset.userManagement.dao.RoleDao;
import lk.avsec_welfare.asset.userManagement.entity.Role;
import lk.avsec_welfare.util.interfaces.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@CacheConfig( cacheNames = {"role"} ) // tells Spring where to store cache for this class
public class RoleService implements AbstractService< Role, Integer > {
  private final RoleDao roleDao;

  @Autowired
  public RoleService(RoleDao roleDao) {
    this.roleDao = roleDao;
  }

  @Cacheable
  public List< Role > findAll() {
    return roleDao.findAll().stream().filter(x -> x.getLiveDead().equals(LiveDead.ACTIVE)).collect(Collectors.toList());
  }

  @Cacheable
  public Role findById(Integer id) {
    return roleDao.getOne(id);
  }


  @Caching( evict = {@CacheEvict( value = "role", allEntries = true )},
      put = {@CachePut( value = "role", key = "#role.id" )} )
  public Role persist(Role role) {
    role.setRoleName(role.getRoleName().toUpperCase());
    if ( role.getId() == null ) {
      role.setLiveDead(LiveDead.ACTIVE);
    }
    return roleDao.save(role);
  }

  @CacheEvict( allEntries = true )
  public boolean delete(Integer id) {
    Role role = roleDao.getOne(id);
    role.setLiveDead(LiveDead.STOP);
    roleDao.save(role);
    return true;
  }

  @Cacheable
  public List< Role > search(Role role) {
    ExampleMatcher matcher = ExampleMatcher
        .matching()
        .withIgnoreCase()
        .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
    Example< Role > roleExample = Example.of(role, matcher);
    return roleDao.findAll(roleExample);
  }

  @Cacheable
  public Role findByRoleName(String roleName) {
    return roleDao.findByRoleName(roleName);
  }
}
