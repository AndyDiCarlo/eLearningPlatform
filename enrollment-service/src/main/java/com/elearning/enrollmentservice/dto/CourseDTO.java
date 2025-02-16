package com.elearning.enrollmentservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseDTO {
    private Long id;
    private String title;
    private String description;
    private String instructor;
    private Integer maxEnrollments;
}
