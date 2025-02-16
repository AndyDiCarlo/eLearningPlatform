package com.elearning.course.service;

import java.util.UUID;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.elearning.course.config.ServiceConfig;
import com.elearning.course.model.Course;
import com.elearning.course.repository.CourseRepository;

@Service
public class CourseService {

    @Autowired
    MessageSource messages;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    ServiceConfig config;


    public Course getCourse(String courseId){
        Course course = courseRepository.findByCourseId(courseId);
        if (null == course) {
            throw new IllegalArgumentException(String.format(messages.getMessage("course.search.error.message", null, null),courseId));
        }
        return course;
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course createCourse(Course course){
        course.setCourseId(UUID.randomUUID().toString());
        courseRepository.save(course);

        return course;
    }

    public Course updateCourse(Course course){
        courseRepository.save(course);

        return course;
    }

    public String deleteCourse(String courseId){
        String responseMessage = null;
        Course course = new Course();
        course.setCourseId(courseId);
        courseRepository.delete(course);
        responseMessage = String.format(messages.getMessage("course.delete.message", null, null),courseId);
        return responseMessage;

    }
}
