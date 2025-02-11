package com.elearning.user.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.elearning.user.config.ServiceConfig;
import com.elearning.user.model.User;
import com.elearning.user.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	MessageSource messages;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	ServiceConfig config;


	public User getUser(String userId){
		User user = userRepository.findByUserId(userId);
		if (null == user) {
			throw new IllegalArgumentException(String.format(messages.getMessage("user.search.error.message", null, null),userId));
		}
		return user.withComment(config.getProperty());
	}

	public User createUser(User user){
		user.setUserId(UUID.randomUUID().toString());
		userRepository.save(user);

		return user.withComment(config.getProperty());
	}

	public User updateUser(User user){
		userRepository.save(user);

		return user.withComment(config.getProperty());
	}

	public String deleteUser(String userId){
		String responseMessage = null;
		User user = new User();
		user.setUserId(userId);
		userRepository.delete(user);
		responseMessage = String.format(messages.getMessage("user.delete.message", null, null),userId);
		return responseMessage;

	}
}
