package com.example.employee.service.impl;

import com.example.employee.domain.UserAndRole;
import com.example.employee.service.UserService;
import com.example.employee.web.schema.EmployeeUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    public UserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<UserAndRole> user = userService.getUser(userName);

        user.orElseThrow(() -> new UsernameNotFoundException("User not found with username:" + userName));

        return new EmployeeUserDetails(user.get());
    }
}
