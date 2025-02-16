package com.elearning.course.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.elearning.course.model.Course;

@Repository
public interface CourseRepository extends CrudRepository<Course,String>  {
    public List<Course> findAll();
    public Course findByCourseId(String courseId);
}
