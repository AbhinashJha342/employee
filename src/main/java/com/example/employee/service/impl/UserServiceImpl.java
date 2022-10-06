package com.example.employee.service.impl;

import com.example.employee.domain.UserAndRole;
import com.example.employee.persistence.UserRepository;
import com.example.employee.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void createUser(UserAndRole user) {
        userRepository.save(user);
    }

    @Override
    public Optional<UserAndRole> getUser(String username) {
        return userRepository.findByUsername(username);
    }
}
