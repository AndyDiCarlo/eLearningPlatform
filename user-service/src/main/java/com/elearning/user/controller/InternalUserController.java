package com.elearning.user.controller;

import com.elearning.user.dto.UserDTO;
import com.elearning.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/internal")
public class InternalUserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value="/users/{userId}", method = RequestMethod.GET)
    public ResponseEntity<UserDTO> getUserDtoInternal(@PathVariable("userId") String userId) {
        UserDTO userDTO = userService.getUserDTO(userId);
        return ResponseEntity.ok(userDTO);
    }
}