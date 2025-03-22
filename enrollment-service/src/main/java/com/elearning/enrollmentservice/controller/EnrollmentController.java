package com.elearning.enrollmentservice.controller;

import com.elearning.enrollmentservice.dto.EnrollmentRequest;
import com.elearning.enrollmentservice.model.Enrollment;
import com.elearning.enrollmentservice.service.EnrollmentService;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enrollments")
public class EnrollmentController {

    @Autowired
    private EnrollmentService enrollmentService;

    /**
     * Enroll a student by passing an EnrollmentRequest object containing full (but abbreviated) User and Course DTOs.
     */
    @RolesAllowed({ "ADMIN", "USER" })
    @PostMapping
    public Enrollment enrollStudent(@RequestBody EnrollmentRequest enrollmentRequest) {
        return enrollmentService.enrollStudent(enrollmentRequest);
    }

    @RolesAllowed({ "ADMIN" })
    @GetMapping
    public List<Enrollment> getAllEnrollments() {
        return enrollmentService.getAllEnrollments();
    }

    @RolesAllowed({ "ADMIN", "USER" })
    @GetMapping("/{id}")
    public Enrollment getEnrollment(@PathVariable Long id) {
        return enrollmentService.getEnrollmentById(id);
    }

    @RolesAllowed({ "ADMIN", "USER" })
    @PutMapping("/{id}")
    public Enrollment updateEnrollment(@PathVariable Long id, @RequestBody Enrollment enrollment) {
        return enrollmentService.updateEnrollment(id, enrollment);
    }

    @RolesAllowed({ "ADMIN" })
    @DeleteMapping("/{id}")
    public void deleteEnrollment(@PathVariable Long id) {
        enrollmentService.deleteEnrollment(id);
    }

    @RolesAllowed({ "ADMIN" })
    @GetMapping("/user/{userId}")
    public List<Enrollment> getEnrollmentsByUserId(@PathVariable String userId) {
        return enrollmentService.getEnrollmentsByUserId(userId);
    }

    @RolesAllowed({ "ADMIN" })
    @GetMapping("/course/{courseId}")
    public List<Enrollment> getEnrollmentsByCourseId(@PathVariable String courseId) {
        return enrollmentService.getEnrollmentsByCourseId(courseId);
    }
}