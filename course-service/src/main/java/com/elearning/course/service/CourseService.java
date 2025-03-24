package com.elearning.course.service;

import java.util.UUID;
import java.util.List;

import com.elearning.course.dto.CourseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.elearning.course.config.ServiceConfig;
import com.elearning.course.model.Course;
import com.elearning.course.repository.CourseRepository;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;

@Service
public class CourseService {

    @Autowired
    MessageSource messages;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    ServiceConfig config;

    @CircuitBreaker(name = "courseService", fallbackMethod = "courseFallback")
    @Retry(name = "retryCourseService", fallbackMethod = "courseFallback")
    @RateLimiter(name = "rateLimiterCourseService", fallbackMethod = "courseFallback")
    @Bulkhead(name = "bulkheadCourseService", fallbackMethod = "courseFallback", type = Bulkhead.Type.SEMAPHORE)
    public Course getCourse(String courseId){
        Course course = courseRepository.findByCourseId(courseId);
        if (null == course) {
            throw new IllegalArgumentException(String.format(messages.getMessage("course.search.error.message", null, null),courseId));
        }
        return course;
    }

    public CourseDTO getCourseDTO(String courseId){
        Course course = courseRepository.findByCourseId(courseId);

        return mapToCourseDTO(course);
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @CircuitBreaker(name = "courseService", fallbackMethod = "courseFallback")
    @Retry(name = "retryCourseService", fallbackMethod = "courseFallback")
    @RateLimiter(name = "rateLimiterCourseService", fallbackMethod = "courseFallback")
    @Bulkhead(name = "bulkheadCourseService", fallbackMethod = "courseFallback", type = Bulkhead.Type.SEMAPHORE)
    public Course createCourse(Course course){
        course.setCourseId(UUID.randomUUID().toString());
        courseRepository.save(course);

        return course;
    }

    @CircuitBreaker(name = "courseService", fallbackMethod = "courseFallback")
    @Retry(name = "retryCourseService", fallbackMethod = "courseFallback")
    @RateLimiter(name = "rateLimiterCourseService", fallbackMethod = "courseFallback")
    @Bulkhead(name = "bulkheadCourseService", fallbackMethod = "courseFallback", type = Bulkhead.Type.SEMAPHORE)
    public Course updateCourse(Course course){
        courseRepository.save(course);

        return course;
    }

    @CircuitBreaker(name = "courseService", fallbackMethod = "courseFallback")
    @Retry(name = "retryCourseService", fallbackMethod = "courseFallback")
    @RateLimiter(name = "rateLimiterCourseService", fallbackMethod = "courseFallback")
    @Bulkhead(name = "bulkheadCourseService", fallbackMethod = "courseFallback", type = Bulkhead.Type.SEMAPHORE)
    public String deleteCourse(String courseId){
        String responseMessage = null;
        Course course = new Course();
        course.setCourseId(courseId);
        courseRepository.delete(course);
        responseMessage = String.format(messages.getMessage("course.delete.message", null, null),courseId);
        return responseMessage;

    }

    private CourseDTO mapToCourseDTO(Course course){
        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setCourseId(course.getCourseId());
        courseDTO.setDescription(course.getDescription());
        courseDTO.setTitle(course.getTitle());
        courseDTO.setInstructor(course.getInstructor());
        courseDTO.setMaxEnrollments(course.getMaxEnrollments());

        return courseDTO;
    }

    public Course courseFallback() {
        Course course = new Course();
        course.setCourseId("0");
        course.setTitle("null");
        course.setInstructor("null");
        course.setDescription("Unable to access users at this time, please try again.");
        return course;
    }
}
