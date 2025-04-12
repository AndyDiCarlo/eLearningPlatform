package com.elearning.enrollmentservice.service.client;


import com.elearning.enrollmentservice.config.FeignClientConfig;
import com.elearning.enrollmentservice.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.elearning.enrollmentservice.model.User;

@FeignClient(value = "user-service", configuration = FeignClientConfig.class)
public interface UserFeignClient {
    @RequestMapping(
            method= RequestMethod.GET,
            value="/internal/users/{userId}",
            consumes="application/json")
    UserDTO getUserDTO(@PathVariable("userId") String userId);
}
