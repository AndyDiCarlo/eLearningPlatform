package com.elearning.course.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.elearning.course.dto.CourseDTO;
import jakarta.annotation.security.RolesAllowed;
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
@RequestMapping(value = "/courses")
public class CourseController {
    @Autowired
    CourseService courseService;

    @RolesAllowed({ "ADMIN" })
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<Object> getAllCourses() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @RolesAllowed({ "ADMIN" })
    @RequestMapping(value = "/{courseId}", method = RequestMethod.GET)
    public ResponseEntity<Object> getCourse(@PathVariable("courseId") String courseId) {
        return ResponseEntity.ok(courseService.getCourse(courseId));
    }

    @RolesAllowed({ "ADMIN", "USER" })
    @RequestMapping(value = "/dto/{courseId}", method = RequestMethod.GET)
    public ResponseEntity<CourseDTO> getCourseDTO(@PathVariable("courseId") String courseId) {

        System.out.println("Transferring DTO...");

        CourseDTO courseDTO = courseService.getCourseDTO(courseId);

        return ResponseEntity.ok(courseDTO);
    }

    @RolesAllowed({ "ADMIN" })
    @PostMapping
    public ResponseEntity<Object> createCourse(@RequestBody Course course) {
        return ResponseEntity.ok(courseService.createCourse(course));
    }

    @RolesAllowed({ "ADMIN" })
    @PutMapping
    public ResponseEntity<Object> updateCourse(@RequestBody Course course) {
        return ResponseEntity.ok(courseService.updateCourse(course));
    }

    @RolesAllowed({ "ADMIN" })
    @DeleteMapping(value = "/{courseId}")
    public ResponseEntity<Object> deleteCourse(@PathVariable("courseId") String courseId) {
        return ResponseEntity.ok(courseService.deleteCourse(courseId));
    }

}
