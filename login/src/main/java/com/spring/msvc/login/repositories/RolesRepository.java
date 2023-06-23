package com.spring.msvc.login.repositories;

import com.spring.msvc.login.dto.RolesDataBase;
import com.spring.msvc.login.entity.Roles;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RolesRepository extends MongoRepository<Roles, String> {

  Optional<Roles> findByName (RolesDataBase name);
}
