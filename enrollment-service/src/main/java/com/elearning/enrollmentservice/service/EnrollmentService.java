package com.elearning.enrollmentservice.service;

import com.elearning.enrollmentservice.dto.EnrollmentRequest;
import com.elearning.enrollmentservice.dto.UserDTO;
import com.elearning.enrollmentservice.dto.CourseDTO;
import com.elearning.enrollmentservice.model.Enrollment;
import com.elearning.enrollmentservice.model.User;
import com.elearning.enrollmentservice.model.Course;
import com.elearning.enrollmentservice.repository.EnrollmentRepository;
import com.elearning.enrollmentservice.repository.UserRepository;
import com.elearning.enrollmentservice.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    @Autowired
    public EnrollmentService(EnrollmentRepository enrollmentRepository,
                             UserRepository userRepository,
                             CourseRepository courseRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
    }

    /**
     * Maps a UserDTO to a User entity with only the abbreviated attributes.
     */
    private User mapToUser(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setUpdatedAt(LocalDateTime.now());
        return user;
    }

    /**
     * Maps a CourseDTO to a Course entity with only the abbreviated attributes.
     */
    private Course mapToCourse(CourseDTO courseDTO) {
        Course course = new Course();
        course.setId(courseDTO.getId());
        course.setTitle(courseDTO.getTitle());
        course.setDescription(courseDTO.getDescription());
        course.setInstructor(courseDTO.getInstructor());
        course.setMaxEnrollments(courseDTO.getMaxEnrollments());
        course.setUpdatedAt(LocalDateTime.now());
        return course;
    }

    /**
     * Enrolls a student using a full EnrollmentRequest (which contains abbreviated User and Course DTOs).
     * If the user or course does not already exist in the repository, it creates one using only the desired attributes.
     */
    public Enrollment enrollStudent(EnrollmentRequest enrollmentRequest) {
        UserDTO userDTO = enrollmentRequest.getUser();
        CourseDTO courseDTO = enrollmentRequest.getCourse();
        String status = enrollmentRequest.getStatus();

        // Check if the user exists; if not, map and save the abbreviated version.
        User user = userRepository.findById(userDTO.getId()).orElse(null);
        if (user == null) {
            user = mapToUser(userDTO);
            user = userRepository.save(user);
        }

        // Check if the course exists; if not, map and save the abbreviated version.
        Course course = courseRepository.findById(courseDTO.getId()).orElse(null);
        if (course == null) {
            course = mapToCourse(courseDTO);
            course = courseRepository.save(course);
        }

        // Create and save the new enrollment.
        Enrollment enrollment = new Enrollment(user, course, status);
        return enrollmentRepository.save(enrollment);
    }

    public List<Enrollment> getAllEnrollments() {
        return enrollmentRepository.findAll();
    }

    public Enrollment getEnrollmentById(Long id) {
        return enrollmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Enrollment not found with id " + id));
    }

    public Enrollment updateEnrollment(Long id, Enrollment updatedEnrollment) {
        Enrollment enrollment = getEnrollmentById(id);
        enrollment.setStatus(updatedEnrollment.getStatus());
        // Optionally update user or course details if required.
        return enrollmentRepository.save(enrollment);
    }

    public void deleteEnrollment(Long id) {
        enrollmentRepository.deleteById(id);
    }

    public List<Enrollment> getEnrollmentsByUserId(Long userId) {
        return enrollmentRepository.findByUser_Id(userId);
    }

    public List<Enrollment> getEnrollmentsByCourseId(Long courseId) {
        return enrollmentRepository.findByCourse_Id(courseId);
    }
}
