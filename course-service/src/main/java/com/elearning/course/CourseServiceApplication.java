package com.elearning.course;

import java.util.Locale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import reactor.core.publisher.Hooks;

@SpringBootApplication
@RefreshScope
public class CourseServiceApplication {

	public static void main(String[] args) {

		Hooks.enableAutomaticContextPropagation();

		SpringApplication.run(CourseServiceApplication.class, args);


	}

	// @Bean
	// public LocaleResolver localeResolver() {
	// 	SessionLocaleResolver localeResolver = new SessionLocaleResolver();
	// 	localeResolver.setDefaultLocale(Locale.US);
	// 	return localeResolver;
	// }
	@Bean
	@Primary
	public ResourceBundleMessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setUseCodeAsDefaultMessage(true);
		messageSource.setBasenames("messages");
		return messageSource;
	}

}
