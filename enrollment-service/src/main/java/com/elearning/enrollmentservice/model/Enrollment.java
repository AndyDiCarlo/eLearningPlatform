package com.elearning.enrollmentservice.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "enrollments")
@Getter
@Setter
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    private LocalDateTime enrollmentDate;
    private String status;

    public Enrollment() {
        this.enrollmentDate = LocalDateTime.now();
    }

    public Enrollment(User user, Course course, String status) {
        this.user = user;
        this.course = course;
        this.status = status;
        this.enrollmentDate = LocalDateTime.now();
    }
}
