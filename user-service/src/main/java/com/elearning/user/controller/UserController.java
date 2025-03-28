package com.elearning.user.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.elearning.user.dto.UserDTO;
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

import com.elearning.user.model.User;
import com.elearning.user.dto.UserDTO;
import com.elearning.user.service.UserService;

@RestController
@RequestMapping(value="/users")
public class UserController {

	@Autowired
	private UserService userService;

	@RolesAllowed({ "ADMIN" })
	@RequestMapping(value="/{userId}",method = RequestMethod.GET)
	public ResponseEntity<User> getUser( @PathVariable("userId") String userId) {
		
		User user = userService.getUser(userId);
		user.add(
				linkTo(methodOn(UserController.class).getUser(userId)).withSelfRel(),
				linkTo(methodOn(UserController.class).createUser(user)).withRel("createUser"),
				linkTo(methodOn(UserController.class).updateUser(user)).withRel("updateUser"),
				linkTo(methodOn(UserController.class).deleteUser(user.getUserId())).withRel("deleteUser")
		);
		
		return ResponseEntity.ok(user);
	}

	@RolesAllowed({ "ADMIN", "USER" })
	@RequestMapping(value="/dto/{userId}",method = RequestMethod.GET)
	public ResponseEntity<UserDTO> getUserDto( @PathVariable("userId") String userId) {

		System.out.println("Transferring DTO...");

		UserDTO userDTO = userService.getUserDTO(userId);

		return ResponseEntity.ok(userDTO);
	}

	@RolesAllowed({ "ADMIN", "USER" })
	@PutMapping
	public ResponseEntity<User> updateUser(@RequestBody User request) {
		return ResponseEntity.ok(userService.updateUser(request));
	}

	@RolesAllowed({ "ADMIN", "USER" })
	@PostMapping
	public ResponseEntity<User> createUser(@RequestBody User request) {
		return ResponseEntity.ok(userService.createUser(request));
	}

	@RolesAllowed({ "ADMIN" })
	@DeleteMapping(value="/{userId}")
	public ResponseEntity<String> deleteUser(@PathVariable("userId") String userId) {
		return ResponseEntity.ok(userService.deleteUser(userId));
	}
}
