package lk.avsec_welfare.asset.course.service;

import lk.avsec_welfare.asset.briefing.entity.Briefing;
import lk.avsec_welfare.asset.common_asset.model.enums.LiveDead;
import lk.avsec_welfare.asset.course.entity.Course;
import lk.avsec_welfare.asset.course.dao.CourseDao;
import lk.avsec_welfare.asset.course.entity.Course;
import lk.avsec_welfare.util.interfaces.AbstractService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
// spring transactional annotation need to tell spring to this method work through the project
@CacheConfig(cacheNames = "course")
public class CourseService implements AbstractService<Course, Integer> {
    private final CourseDao courseDao;

    public CourseService(CourseDao courseDao) {
        this.courseDao = courseDao;
    }

    public List<Course> findAll() {
        return courseDao.findAll().stream().filter(x->x.getLiveDead().equals(LiveDead.ACTIVE)).collect(Collectors.toList());
    }

    public Course findById(Integer id) {
        return courseDao.getOne(id);
    }

    public Course persist(Course course) {
        if ( course.getId() ==null ){
            course.setLiveDead(LiveDead.ACTIVE);
        }
        return courseDao.save(course);
    }

    public boolean delete(Integer id) {
        Course course = courseDao.getOne(id);
        course.setLiveDead(LiveDead.STOP);
        courseDao.save(course);
        return false;
    }


    public List<Course> search(Course course) {
        return null;
    }



/*    public List<Dependency> findByProvince(Province province) {
        return dependencyDao.findByProvince(province);
    }*/
}
