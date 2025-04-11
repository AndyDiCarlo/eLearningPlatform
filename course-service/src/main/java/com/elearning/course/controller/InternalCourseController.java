package com.elearning.course.controller;

import com.elearning.course.dto.CourseDTO;
import com.elearning.course.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/internal")
public class InternalCourseController {

    @Autowired
    private CourseService courseService;

    @RequestMapping(value="/courses/{courseId}", method = RequestMethod.GET)
    public ResponseEntity<CourseDTO> getCourseDtoInternal(@PathVariable("courseId") String courseId) {
        CourseDTO courseDTO = courseService.getCourseDTO(courseId);
        return ResponseEntity.ok(courseDTO);
    }
}
