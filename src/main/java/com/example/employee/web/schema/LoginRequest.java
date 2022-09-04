package com.example.employee.web.schema;

import javax.validation.constraints.NotNull;

public class LoginRequest {

    @NotNull(message = "username cannot be null")
    private final String username;

    @NotNull(message = "email cannot be null")
    private final String emailId;

    @NotNull(message = "password cannot be null")
    private final String password;

    public LoginRequest(String username, String emailId, String password) {
        this.username = username;
        this.emailId = emailId;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getEmailId() {
        return emailId;
    }

    public String getPassword() {
        return password;
    }
}
