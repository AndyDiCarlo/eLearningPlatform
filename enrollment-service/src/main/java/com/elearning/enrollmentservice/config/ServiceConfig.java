package com.elearning.enrollmentservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix = "enrollment-service")
@Getter @Setter
public class ServiceConfig{

    private String property;
    private String serviceUrl;
    private String serviceName = "enrollment-service";
    private String apiVersion = "v1";
    
}
