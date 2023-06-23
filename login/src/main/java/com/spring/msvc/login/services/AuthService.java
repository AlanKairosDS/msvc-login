package com.spring.msvc.login.services;

import com.spring.common.tools.entity.RestResponse;
import com.spring.msvc.login.dto.LoginRequest;
import com.spring.msvc.login.dto.SingupRequest;
import org.springframework.http.ResponseEntity;

public interface AuthService {

  ResponseEntity<RestResponse<Object>> registrarUsuario (SingupRequest singupRequest);

  ResponseEntity<RestResponse<Object>> autenticarUsuario (LoginRequest loginRequest);


}
