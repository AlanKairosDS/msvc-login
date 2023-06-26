package com.spring.msvc.login.controllers;

import com.spring.common.tools.entity.RestResponse;
import com.spring.msvc.login.dto.LoginRequest;
import com.spring.msvc.login.dto.SingupRequest;
import com.spring.msvc.login.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:9000", maxAge = 3600, allowCredentials="true")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

  @Autowired
  private AuthService authService;

  @PostMapping(
          value = "/registrar-usuario",
          produces = {MediaType.APPLICATION_JSON_VALUE},
          consumes = {MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<RestResponse<Object>> registrarUsuario (@Valid @RequestBody SingupRequest singupRequest) {
    return authService.registrarUsuario(singupRequest);
  }

  @PostMapping(
          value = "/iniciar-sesion",
          produces = {MediaType.APPLICATION_JSON_VALUE},
          consumes = {MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<RestResponse<Object>> autenticarUsuario (@Valid @RequestBody LoginRequest loginRequest) {
    return authService.autenticarUsuario(loginRequest);
  }


}
