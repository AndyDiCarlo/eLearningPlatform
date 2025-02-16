package com.elearning.enrollmentservice.model;

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
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Title is required")
    @Size(max = 255)
    private String title;

    @Column(length = 2000)
    private String description;

    @NotBlank(message = "Instructor is required")
    private String instructor;

    private LocalDateTime updatedAt;

    @Column(nullable = false)
    private Integer maxEnrollments;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @OneToMany(mappedBy = "course")
    private List<Enrollment> enrollments;


    public Course() {
        this.updatedAt = LocalDateTime.now();
    }

    public Course(Long id, String title, String description, String instructor) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.instructor = instructor;
        this.updatedAt = LocalDateTime.now();
    }
}
