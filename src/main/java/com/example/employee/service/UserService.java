package com.example.employee.service;

import com.example.employee.domain.UserAndRole;

public interface UserService {

    void createUser(UserAndRole user);

    UserAndRole getUser(String userName);
}
