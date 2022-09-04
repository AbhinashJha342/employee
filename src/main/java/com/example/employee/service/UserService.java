package com.example.employee.service;

import com.example.employee.domain.User;

public interface UserService {

    void createUser(User user);

    User getUser(String userName);
}
