package com.estudos.chat.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.estudos.chat.domain.entity.Usuario;
import com.estudos.chat.domain.repository.UsuarioRepository;
import com.estudos.chat.infra.security.TokenService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    public Optional<Usuario> login(Usuario dto, HttpServletRequest request){
        var usernamePassword = new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getSenha());

        var auth = this.manager.authenticate(usernamePassword);

        System.out.println("aa");
        
        var token = tokenService.generateToken((Usuario) auth.getPrincipal());

        System.out.println("aa");

        Usuario usuario = (Usuario) auth.getPrincipal();

        System.out.println("aa");

        System.out.println(token);

        return Optional.of(usuario);
    }

    public Optional<Usuario> register(@Valid Usuario dto) {
        String passaword = dto.getSenha();
        String encryptedPassword = new BCryptPasswordEncoder().encode(passaword);

        dto.setSenha(encryptedPassword);

        repository.save(dto);

        return Optional.of(dto);
    }

}
