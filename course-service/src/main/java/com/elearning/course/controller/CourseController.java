package com.elearning.course.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.elearning.course.model.Course;
import com.elearning.course.service.CourseService;

@RestController
@RequestMapping(value = "/v1/elearning/courses")
public class CourseController {
    @Autowired
    CourseService courseService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<Object> getAllCourses() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @RequestMapping(value = "/{courseId}", method = RequestMethod.GET)
    public ResponseEntity<Object> getCourse(@PathVariable("courseId") String courseId) {
        return ResponseEntity.ok(courseService.getCourse(courseId));
    }

    @PostMapping
    public ResponseEntity<Object> createCourse(@RequestBody Course course) {
        return ResponseEntity.ok(courseService.createCourse(course));
    }

    @PutMapping
    public ResponseEntity<Object> updateCourse(@RequestBody Course course) {
        return ResponseEntity.ok(courseService.updateCourse(course));
    }

    @DeleteMapping(value = "/{courseId}")
    public ResponseEntity<Object> deleteCourse(@PathVariable("courseId") String courseId) {
        return ResponseEntity.ok(courseService.deleteCourse(courseId));
    }

}
