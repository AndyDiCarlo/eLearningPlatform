package com.elearning.enrollmentservice.repository;

import com.elearning.enrollmentservice.model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    List<Enrollment> findByUser_UserId(String userId);
    List<Enrollment> findByCourse_CourseId(String courseId);
}
