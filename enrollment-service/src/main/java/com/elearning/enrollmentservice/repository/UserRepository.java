package com.elearning.enrollmentservice.repository;

import com.elearning.enrollmentservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    // Additional query methods as needed.
}
