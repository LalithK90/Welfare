package lk.avsec_welfare.asset.course.dao;

import lk.avsec_welfare.asset.course.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseDao extends JpaRepository<Course, Integer> {

/*//select * from district where province = ?1
    List<Qualification> findByProvince(Province province);*/

}
