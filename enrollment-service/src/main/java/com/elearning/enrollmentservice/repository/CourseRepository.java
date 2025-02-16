package com.elearning.enrollmentservice.repository;

import com.elearning.enrollmentservice.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
    // Additional query methods as needed.
}
