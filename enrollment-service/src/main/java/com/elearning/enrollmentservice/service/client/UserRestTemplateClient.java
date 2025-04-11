package com.elearning.enrollmentservice.service.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.elearning.enrollmentservice.model.User;

@Component
public class UserRestTemplateClient {

    @Autowired
    private WebClient webClient;

    public User getUser(String userId) {
        return webClient
                .get()
                .uri("http://gateway:8072/user/users/{userId}", userId)
                .retrieve()
                .bodyToMono(User.class)
                .block(); // Blocks until the response is available
    }

    
}
