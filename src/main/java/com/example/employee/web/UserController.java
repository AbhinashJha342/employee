package com.example.employee.web;

import com.example.employee.service.UserService;
import com.example.employee.web.schema.UserDetailsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    ResponseEntity createUser(@RequestBody UserDetailsDTO userDetails) {
        userService.createUser(UserDetailsDTO.to(userDetails));
        return new ResponseEntity(HttpStatus.OK);
    }

}
