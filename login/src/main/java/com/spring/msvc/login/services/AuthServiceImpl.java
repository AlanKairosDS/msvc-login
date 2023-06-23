package com.spring.msvc.login.services;

import com.spring.common.tools.entity.RestResponse;
import com.spring.common.tools.service.UtilService;
import com.spring.msvc.login.dto.LoginRequest;
import com.spring.msvc.login.dto.LoginResponse;
import com.spring.msvc.login.dto.RolesDataBase;
import com.spring.msvc.login.dto.SingupRequest;
import com.spring.msvc.login.entity.Roles;
import com.spring.msvc.login.entity.Usuarios;
import com.spring.msvc.login.repositories.RolesRepository;
import com.spring.msvc.login.repositories.UsuariosRepository;
import com.spring.msvc.login.security.jwt.JwtUtils;
import com.spring.msvc.login.security.services.UserDetailsImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {

  private static final Logger LOGGER = LogManager.getLogger("login");

  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  UsuariosRepository usuariosRepository;

  @Autowired
  RolesRepository rolesRepository;

  @Autowired
  PasswordEncoder passwordEncoder;

  @Autowired
  JwtUtils jwtUtils;

  @Override
  public ResponseEntity<RestResponse<Object>> registrarUsuario (SingupRequest singupRequest) {

    if (usuariosRepository.existsByUsername(singupRequest.getUsername())) {
      return new UtilService().armarRespuesta(
              HttpStatus.BAD_REQUEST.value(),
              "Ocurrio un error al registrar usuario",
              "El nombre de usuario ingresado ya se encuentra en uso",
              true,
              null,
              HttpStatus.BAD_REQUEST
      );
    }

    if (usuariosRepository.existsByEmail(singupRequest.getEmail())) {
      return new UtilService().armarRespuesta(
              HttpStatus.BAD_REQUEST.value(),
              "Ocurrio un error al registrar usuario",
              "El email ingresado ya se encuentra en uso",
              true,
              null,
              HttpStatus.BAD_REQUEST
      );
    }

    return registrarUsuarioValido(singupRequest);
  }

  public ResponseEntity<RestResponse<Object>> registrarUsuarioValido (SingupRequest singupRequest) {

    Usuarios usuarios = Usuarios.builder()
            .username(singupRequest.getUsername())
            .email(singupRequest.getEmail())
            .password(passwordEncoder.encode(singupRequest.getPassword()))
            .build();

    Set<String> strRoles = singupRequest.getRoles();
    Set<Roles> roles = new HashSet<>();

    if (strRoles == null) {
      Roles userRole = rolesRepository.findByName(RolesDataBase.ROLE_USER).orElseThrow(
              () -> new RuntimeException("No se encontro el ROL")
      );
      roles.add(userRole);
    }
    else {
      strRoles.forEach(role -> {
        switch (role) {
          case "admin" -> {
            Roles adminRole = rolesRepository.findByName(RolesDataBase.ROLE_ADMIN).orElseThrow(
                    () -> new RuntimeException("No se encontro el ROL ADMIN")
            );
            roles.add(adminRole);
          }
          case "mod" -> {
            Roles modRole = rolesRepository.findByName(RolesDataBase.ROLE_MODERATOR).orElseThrow(
                    () -> new RuntimeException("No se encontro el ROL MODERATOR")
            );
            roles.add(modRole);
          }
          default -> {
            Roles userRole = rolesRepository.findByName(RolesDataBase.ROLE_USER).orElseThrow(
                    () -> new RuntimeException("No se encontro el ROL USER")
            );
            roles.add(userRole);
          }
        }
      });
    }

    usuarios.setRoles(roles);
    usuariosRepository.save(usuarios);

    return new UtilService().armarRespuesta(
            HttpStatus.OK.value(),
            "Proceso ejecutado correctamente",
            "Usuario registrado de forma correcta",
            true,
            null,
            HttpStatus.OK
    );
  }

  @Override
  public ResponseEntity<RestResponse<Object>> autenticarUsuario (LoginRequest loginRequest) {

    Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(),
                    loginRequest.getPassword()
            )
    );

    SecurityContextHolder.getContext().setAuthentication(authentication);

    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

    ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

    List<String> roles = userDetails.getAuthorities().stream()
            .map(item -> item.getAuthority())
            .collect(Collectors.toList());

    RestResponse<Object> restResponse = RestResponse.builder()
            .error(false)
            .code(HttpStatus.OK.value())
            .message(HttpStatus.OK.getReasonPhrase())
            .description("Usuario autenticado de forma correcta")
            .data(
                    LoginResponse.builder()
                            .id(userDetails.getId())
                            .username(userDetails.getUsername())
                            .email(userDetails.getEmail())
                            .roles(roles)
                            .build()
            )
            .build();

    return ResponseEntity
            .ok()
            .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
            .body(restResponse);
  }


}
