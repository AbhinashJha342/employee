package com.example.employee.service;

import com.example.employee.web.schema.LoginRequest;
import org.springframework.security.core.Authentication;

import java.util.Optional;

public interface AuthService {

    Optional<Authentication> authenticateUser(LoginRequest loginRequest);
}
