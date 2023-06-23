package com.spring.msvc.login.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

  @NotNull(message = "El Nombre de Usuario no puede ser NULL")
  @NotBlank(message = "El Nombre de Usuario no puede estar vacio")
  private String username;

  @NotNull(message = "El Password no puede ser NULL")
  @NotBlank(message = "El Password no puede estar vacio")
  private String password;
}
