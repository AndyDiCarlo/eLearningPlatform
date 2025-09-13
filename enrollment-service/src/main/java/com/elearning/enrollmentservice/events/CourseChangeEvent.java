package com.elearning.enrollmentservice.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseChangeEvent {
    private String courseId;
    private String eventType;
    private LocalDateTime timestamp;
}