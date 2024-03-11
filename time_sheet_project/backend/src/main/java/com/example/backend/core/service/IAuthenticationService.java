package com.example.backend.core.service;

import com.example.backend.core.model.User;
import com.example.backend.core.req_model.AuthenticationRequest;
import com.example.backend.core.req_model.RegisterRequest;
import com.example.backend.core.response_model.AuthenticationResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface IAuthenticationService {

    AuthenticationResponse register(RegisterRequest request);

    AuthenticationResponse authenticate(AuthenticationRequest request);

    void saveUserToken(User user, String jwtToken);

    void revokeAllUserTokens(User user);

    void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response);

}
