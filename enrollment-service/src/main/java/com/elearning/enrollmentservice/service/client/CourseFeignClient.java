package com.elearning.enrollmentservice.service.client;

import com.elearning.enrollmentservice.config.FeignClientConfig;
import com.elearning.enrollmentservice.dto.CourseDTO;
import com.elearning.enrollmentservice.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.elearning.enrollmentservice.model.User;

@FeignClient(value = "course-service", configuration = FeignClientConfig.class)
public interface CourseFeignClient {
    @RequestMapping(
            method= RequestMethod.GET,
            value="/internal/courses/{courseId}",
            consumes="application/json")
    CourseDTO getCourseDTO(@PathVariable("courseId") String courseId);
}
