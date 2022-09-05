package com.example.employee.service;

import com.example.employee.domain.UserAndRole;

import java.util.Optional;

public interface UserService {

    void createUser(UserAndRole user);

    Optional<UserAndRole> getUser(String userName);
}
