package com.spring.msvc.login.repositories;

import com.spring.msvc.login.entity.Usuarios;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UsuariosRepository extends MongoRepository<Usuarios, String> {

  Optional<Usuarios> findByUsername (String username);

  Boolean existsByUsername (String username);

  Boolean existsByEmail (String email);
}
