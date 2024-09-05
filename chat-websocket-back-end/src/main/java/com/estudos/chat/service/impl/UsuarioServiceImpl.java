package com.estudos.chat.service.impl;

import java.util.Optional;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ProblemDetail;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.estudos.chat.infra.security.dtos.TokenDTO;
import com.estudos.chat.infra.security.dtos.TokenRefreshDTO;
import com.estudos.chat.infra.security.service.TokenService;
import com.estudos.chat.model.dtos.request.UsuarioCadastroRequestDTO;
import com.estudos.chat.model.dtos.request.UsuarioLoginRequestDTO;
import com.estudos.chat.model.dtos.response.UsuarioResponseDTO;
import com.estudos.chat.model.entity.Usuario;
import com.estudos.chat.repository.UsuarioRepository;
import com.estudos.chat.service.UsuarioService;
import com.estudos.chat.util.mapper.MapperS;
import com.estudos.chat.util.mapper.MapperSImpl;
import com.fasterxml.jackson.databind.RuntimeJsonMappingException;

import ch.qos.logback.core.subst.Token;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    private MapperS mapper = Mappers.getMapper(MapperS.class);

    @Override
    public Optional<UsuarioResponseDTO> login(UsuarioLoginRequestDTO dto, HttpServletRequest request,
            HttpServletResponse response) {
        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(dto.email(), dto.senha());

            var auth = this.manager.authenticate(usernamePassword);

            Usuario usuario = (Usuario) auth.getPrincipal();

            UsuarioResponseDTO responseDTO = UsuarioResponseDTO.builder().email(usuario.getEmail())
                    .nome(usuario.getNome())
                    .role(usuario.getRole()).build();
            
            tokenService.salvarTokenCookie((Usuario) auth.getPrincipal(), response);

            return Optional.of(responseDTO);
        } catch (AuthenticationException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<String> register(@Valid UsuarioCadastroRequestDTO dto) {
        String passaword = dto.senha();
        String encryptedPassword = new BCryptPasswordEncoder().encode(passaword);

        Usuario usuario = mapper.toEntity(dto);
        usuario.setSenha(encryptedPassword);
        repository.save(usuario);

        return Optional.of("Foi");
    }

    @Override
    public Optional<Void> obterRefreshToken(String refreshToken, HttpServletResponse response) {
        var subject = tokenService.validateToken(refreshToken);

        UserDetails usuario = repository.findByEmail(subject);

        if (usuario == null)
            throw new RuntimeJsonMappingException("Token invalido");

        var auth = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(auth);

        tokenService.salvarTokenCookie((Usuario) auth.getPrincipal(), response);

        return Optional.ofNullable(null);
    }

}
