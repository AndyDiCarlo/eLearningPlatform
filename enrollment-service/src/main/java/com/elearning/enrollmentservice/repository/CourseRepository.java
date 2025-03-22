package com.elearning.enrollmentservice.repository;

import com.elearning.enrollmentservice.model.Course;
import com.elearning.enrollmentservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {

    Optional<Course> findByCourseId(String courseId);

}
