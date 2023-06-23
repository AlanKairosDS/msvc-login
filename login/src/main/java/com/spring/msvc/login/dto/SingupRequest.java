package com.spring.msvc.login.dto;

import com.spring.msvc.login.entity.Roles;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.Set;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SingupRequest {

  @NotNull(message = "El Nombre de Usuario no puede ser NULL")
  @NotBlank(message = "El Nombre de Usuario no puede estar vacio")
  @Size(min = 5, max = 20)
  private String username;

  @NotNull(message = "El Email no puede ser NULL")
  @NotBlank(message = "El Email no puede estar vacio")
  @Size(max = 50)
  @Email
  private String email;

  @NotNull(message = "El Password no puede ser NULL")
  @NotBlank(message = "El Password no puede estar vacio")
  @Size(min = 8, max = 40)
  private String password;

  @DBRef
  private Set<Roles> roles;
}
