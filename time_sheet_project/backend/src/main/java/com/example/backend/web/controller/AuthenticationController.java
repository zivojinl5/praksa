package com.example.backend.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.core.req_model.AuthenticationRequest;
import com.example.backend.core.req_model.RegisterRequest;
import com.example.backend.core.response_model.AuthenticationResponse;
import com.example.backend.core.service_Implementation.AuthenticationServiceImpl;
import com.example.backend.mapper.UserMapper;
import com.example.backend.web.req_dto.AuthenticationRequestDTO;
import com.example.backend.web.req_dto.RegisterRequestDTO;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

  private final AuthenticationServiceImpl service;
  private final UserMapper mapper;

  @PostMapping("/register")
  public ResponseEntity<AuthenticationResponse> register(
      @RequestBody RegisterRequestDTO requestDTO) {
    RegisterRequest request = mapper.registerReqDTOToRegisterReq(requestDTO);
    return ResponseEntity.ok(service.register(request));
  }

  @PostMapping("/authenticate")
  public ResponseEntity<AuthenticationResponse> authenticate(
      @RequestBody AuthenticationRequestDTO requestDTO) {

    AuthenticationRequest request = mapper.authReqDTOToAuthReq(requestDTO);
    return ResponseEntity.ok(service.authenticate(request));
  }

  @PostMapping("/refresh-token")
  public void refreshToken(
      HttpServletRequest request,
      HttpServletResponse response) throws IOException {
    service.refreshToken(request, response);
  }

}
