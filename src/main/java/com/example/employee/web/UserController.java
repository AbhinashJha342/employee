package com.example.employee.web;

import com.example.employee.service.UserService;
import com.example.employee.web.schema.UserDetailsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/user")
public class UserController {

    @Autowired
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    ResponseEntity createUser(UserDetailsDTO userDetails) {
        userService.createUser(UserDetailsDTO.to(userDetails));
        return new ResponseEntity(HttpStatus.OK);
    }

}
