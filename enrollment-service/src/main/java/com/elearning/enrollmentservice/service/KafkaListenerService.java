package com.elearning.enrollmentservice.service;

import com.elearning.enrollmentservice.dto.CourseDTO;
import com.elearning.enrollmentservice.dto.UserDTO;
import com.elearning.enrollmentservice.events.CourseChangeEvent;
import com.elearning.enrollmentservice.events.UserChangeEvent;
import com.elearning.enrollmentservice.service.client.CourseFeignClient;
import com.elearning.enrollmentservice.service.client.UserFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaListenerService {

    private final EnrollmentService enrollmentService;
    private final UserFeignClient userFeignClient;
    private final CourseFeignClient courseFeignClient;

    @Autowired
    public KafkaListenerService(
            EnrollmentService enrollmentService,
            UserFeignClient userFeignClient,
            CourseFeignClient courseFeignClient) {
        this.enrollmentService = enrollmentService;
        this.userFeignClient = userFeignClient;
        this.courseFeignClient = courseFeignClient;
    }

    @KafkaListener(
            topics = "user-changes",
            containerFactory = "userKafkaListenerContainerFactory",
            groupId = "${spring.application.name}"
    )
    public void listenUserChanges(UserChangeEvent event) {
        log.info("Received user change event: {}", event);

        if ("UPDATE".equals(event.getEventType())) {
            try {
                // Fetch updated user data via REST call
                UserDTO updatedUser = userFeignClient.getUserDTO(event.getUserId());

                // Update local user data in enrollment service
                enrollmentService.updateLocalUserData(event.getUserId(), updatedUser);
                log.info("Successfully updated local user data for userId: {}", event.getUserId());
            } catch (Exception e) {
                log.error("Error processing user update event: {}", e.getMessage(), e);
            }
        } else if ("DELETE".equals(event.getEventType())) {
            // Handle delete events if needed
            log.info("User delete event received for userId: {}", event.getUserId());
        }
    }

    @KafkaListener(
            topics = "course-changes",
            containerFactory = "courseKafkaListenerContainerFactory",
            groupId = "${spring.application.name}"
    )
    public void listenCourseChanges(CourseChangeEvent event) {
        log.info("Received course change event: {}", event);

        if ("UPDATE".equals(event.getEventType())) {
            try {
                // Fetch updated course data via REST call
                CourseDTO updatedCourse = courseFeignClient.getCourseDTO(event.getCourseId());

                // Update local course data in enrollment service
                enrollmentService.updateLocalCourseData(event.getCourseId(), updatedCourse);
                log.info("Successfully updated local course data for courseId: {}", event.getCourseId());
            } catch (Exception e) {
                log.error("Error processing course update event: {}", e.getMessage(), e);
            }
        } else if ("DELETE".equals(event.getEventType())) {
            // Handle delete events if needed
            log.info("Course delete event received for courseId: {}", event.getCourseId());
        }
    }
}