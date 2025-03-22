package com.elearning.user.service;

import java.util.UUID;

import com.elearning.user.dto.UserDTO;
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

	public UserDTO getUserDTO(String userId){
		User user = userRepository.findByUserId(userId);

        return mapUserToDTO(user);
	}

	private UserDTO mapUserToDTO (User user){
		UserDTO userDTO = new UserDTO();
		userDTO.setUserId(user.getUserId());
		userDTO.setUsername(user.getUsername());
		userDTO.setEmail(user.getEmail());
		userDTO.setFirstName(user.getFirstName());
		userDTO.setLastName(user.getLastName());
		return userDTO;
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
