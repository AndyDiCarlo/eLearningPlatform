package com.elearning.enrollmentservice.service.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.elearning.enrollmentservice.model.User;

@Component
public class UserDiscoveryClient {

    @Autowired
    private DiscoveryClient discoveryClient;

    public User getUser(String userId) {
        RestTemplate restTemplate = new RestTemplate();
        List<ServiceInstance> instances = discoveryClient.getInstances("user-service");

        if (instances.size()==0) return null;
        String serviceUri = String.format("%s/users/%s",instances.get(0).getUri().toString(), userId);
    
        ResponseEntity< User > restExchange =
                restTemplate.exchange(
                        serviceUri,
                        HttpMethod.GET,
                        null, User.class, userId);

        return restExchange.getBody();
    }
}
