package com.elearning.enrollmentservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@ToString(exclude = {"enrollments"})
public class User {

    @Id
    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Username is required")
    private String username;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Email is required")
    private String email;

    @Column(name= "first_name")
    @NotBlank(message = "First name is required")
    private String firstName;

    @Column(name= "last_name")
    @NotBlank(message = "Last name is required")
    private String lastName;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Enrollment> enrollments;

    public User() {
        this.updatedAt = LocalDateTime.now();
    }

    public User(String userId, String username, String email, String firstName, String lastName) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.updatedAt = LocalDateTime.now();
    }

}
