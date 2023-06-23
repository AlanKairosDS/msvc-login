package com.spring.msvc.login.security.services;

import com.spring.msvc.login.entity.Usuarios;
import com.spring.msvc.login.repositories.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  private UsuariosRepository usuariosRepository;

  @Override
  @Transactional
  public UserDetails loadUserByUsername (String username) throws UsernameNotFoundException {
    Usuarios user = usuariosRepository.findByUsername(username)
            .orElseThrow(
                    () -> new UsernameNotFoundException(
                            "Usuario no encontrado con username: " + username
                    )
            );

    return UserDetailsImpl.build(user);
  }
}
