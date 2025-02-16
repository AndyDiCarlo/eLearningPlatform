package com.elearning.enrollmentservice.dto;

import com.elearning.enrollmentservice.model.User;
import com.elearning.enrollmentservice.model.Course;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnrollmentRequest {
    private UserDTO user;
    private CourseDTO course;
    private String status;
}
