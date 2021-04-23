package lk.avsec_welfare.asset.userManagement.service;

import lk.avsec_welfare.asset.common_asset.model.enums.LiveDead;
import lk.avsec_welfare.asset.employee.entity.Employee;
import lk.avsec_welfare.asset.userManagement.dao.UserDao;
import lk.avsec_welfare.asset.userManagement.entity.User;
import lk.avsec_welfare.util.interfaces.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@CacheConfig( cacheNames = {"user"} ) // tells Spring where to store cache for this class
public class UserService implements AbstractService< User, Integer > {
  private final UserDao userDao;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public UserService(PasswordEncoder passwordEncoder, UserDao userDao) {
    this.passwordEncoder = passwordEncoder;
    this.userDao = userDao;
  }

  @Cacheable
  public List< User > findAll() {
    return userDao.findAll()
        .stream().filter(x -> x.getLiveDead().equals(LiveDead.ACTIVE)).collect(Collectors.toList());
  }

  @Cacheable
  @Transactional
  public User findById(Integer id) {
    return userDao.getOne(id);
  }

  @Caching( evict = {@CacheEvict( value = "user", allEntries = true )},
      put = {@CachePut( value = "user", key = "#user.id" )} )
  @Transactional
  public User persist(User user) {
    user.setUsername(user.getUsername().toLowerCase());
    if ( user.getId() == null ) {
      user.setLiveDead(LiveDead.ACTIVE);
    }
    if ( user.getPassword() != null ) {
      user.setPassword(passwordEncoder.encode(user.getPassword()));
    } else {
      user.setPassword(userDao.getOne(user.getId()).getPassword());
    }
    return userDao.save(user);
  }

  @CacheEvict( allEntries = true )
  public boolean delete(Integer id) {
    User user = userDao.getOne(id);
    user.setLiveDead(LiveDead.STOP);
    userDao.save(user);
    return false;
  }

  @Cacheable
  public List< User > search(User user) {
    ExampleMatcher matcher =
        ExampleMatcher.matching().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
    Example< User > userExample = Example.of(user, matcher);
    return userDao.findAll(userExample);
  }

  @Cacheable
  public Integer findByUserIdByUserName(String userName) {
    return userDao.findUserIdByUserName(userName);
  }

  @Cacheable
  @Transactional( readOnly = true )
  public User findByUserName(String name) {
    return userDao.findByUsername(name);
  }

  @Cacheable
  public User findUserByEmployee(Employee employee) {
    return userDao.findByEmployee(employee);
  }

  @Cacheable
  public boolean findByEmployee(Employee employee) {
    return userDao.findByEmployee(employee) == null;
  }


}
