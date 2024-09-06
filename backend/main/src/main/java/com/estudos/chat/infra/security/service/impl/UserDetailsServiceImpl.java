package com.estudos.chat.infra.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.estudos.chat.repository.postgresql.UsuarioRepository;
import com.estudos.domain_ms.entity.Usuario;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario userDetails = repository.findByEmail(username);
        if (userDetails == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new UserDetailsImpl(userDetails);
    }

}
