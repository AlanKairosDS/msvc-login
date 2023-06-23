package com.spring.msvc.login.entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "usuarios")
public class Usuarios {

  @Id
  private String id;

  @NotNull(message = "El Nombre de Usuario no puede ser NULL")
  @NotBlank(message = "El Nombre de Usuario no puede estar vacio")
  @Size(max = 20)
  private String username;

  @NotNull(message = "El Email no puede ser NULL")
  @NotBlank(message = "El Email no puede estar vacio")
  @Size(max = 50)
  @Email
  private String email;

  @NotNull(message = "El Password no puede ser NULL")
  @NotBlank(message = "El Password no puede estar vacio")
  @Size(max = 50)
  private String password;

  @DBRef
  private Set<Roles> roles = new HashSet<>();
}
