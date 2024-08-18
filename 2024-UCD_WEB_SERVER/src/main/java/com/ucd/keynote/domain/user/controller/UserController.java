package com.ucd.keynote.domain.user.controller;

import com.ucd.keynote.domain.user.dto.UserDTO;
import com.ucd.keynote.domain.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody UserDTO userDto){
        UserDTO createdUser = userService.registerUser(userDto);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }
}
