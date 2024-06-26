package com.modasby;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class AuthenticationService {
    List<String> users = new ArrayList<>();

    public AuthenticationService() {
        this.users.add("giulliano");
    }

    @Inject
    JWTUtil jwtUtil;

    public boolean authenticate(String token) {
        return jwtUtil.authenticateToken(token);
    }

    public String addUser(CredentialsDto credentialsDto) {
        users.add(credentialsDto.user());
        return jwtUtil.generateToken(credentialsDto.user());
    }
}
