package com.elearning.enrollmentservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "courses")
@Getter
@Setter
@ToString(exclude = {"enrollments"})
public class Course {

    @Id
    @Column(name = "course_id", nullable = false)
    private String courseId;

    @Column(nullable = false)
    @NotBlank(message = "Title is required")
    @Size(max = 255)
    private String title;

    @Column(length = 2000)
    private String description;

    @NotBlank(message = "Instructor is required")
    private String instructor;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(nullable = false, name = "max_enrollments")
    private Integer maxEnrollments;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @OneToMany(mappedBy = "course")
    @JsonIgnore
    private List<Enrollment> enrollments;


    public Course() {
        this.updatedAt = LocalDateTime.now();
    }

    public Course(String courseId, String title, String description, String instructor) {
        this.courseId = courseId;
        this.title = title;
        this.description = description;
        this.instructor = instructor;
        this.updatedAt = LocalDateTime.now();
    }
}
