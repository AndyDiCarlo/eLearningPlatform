package com.elearning.enrollmentservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseDTO {
    private String courseId;
    private String title;
    private String description;
    private String instructor;
    private Integer maxEnrollments;
}
