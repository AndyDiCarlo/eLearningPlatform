package com.elearning.user.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.elearning.user.model.User;

@Repository
public interface UserRepository extends CrudRepository<User,String>  {
    public User findByUserId(String userId);
}
