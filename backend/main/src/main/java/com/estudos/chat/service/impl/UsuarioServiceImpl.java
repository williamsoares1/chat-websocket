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

import com.estudos.domain_ms.dtos.request.UsuarioLoginRequestDTO;
import com.estudos.domain_ms.dtos.request.UsuarioRegisterRequestDTO;
import com.estudos.domain_ms.dtos.response.UsuarioResponseDTO;
import com.estudos.domain_ms.entity.Usuario;
import com.estudos.chat.infra.security.service.TokenService;
import com.estudos.chat.infra.security.service.impl.UserDetailsImpl;
import com.estudos.chat.repository.postgresql.UsuarioRepository;
import com.estudos.chat.service.UsuarioService;
import com.estudos.chat.util.crypt.AESUtil;
import com.estudos.chat.util.mapper.MapperS;
import com.fasterxml.jackson.databind.RuntimeJsonMappingException;

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

            UserDetails userDetails = (UserDetails) auth.getPrincipal();

            Usuario usuario = repository.findByEmail(userDetails.getUsername());
            if (usuario == null) {
                throw new RuntimeException("Usuário não encontrado para o email fornecido.");
            }

            tokenService.salvarTokenCookie(usuario, response);

            return Optional.of(response(usuario));
        } catch (AuthenticationException e) {
            throw new RuntimeException("Erro ao processar o login: " + e.getMessage(), e);
        } catch (RuntimeException e) {
            throw new RuntimeException("Erro ao processar o login: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<String> register(@Valid UsuarioRegisterRequestDTO dto) {
        String passaword = dto.senha();
        String encryptedPassword = new BCryptPasswordEncoder().encode(passaword);

        Usuario usuario = mapper.toEntity(dto);
        usuario.setSenha(encryptedPassword);
        repository.save(usuario);

        return Optional.of("Foi");
    }

    @Override
    public Optional<UsuarioResponseDTO> obterRefreshToken(HttpServletRequest request, HttpServletResponse response) {
        var cookies = request.getCookies();

        if (cookies != null) {
            for (var cookie : cookies) {
                if ("refreshToken".equals(cookie.getName())) {
                    String token = cookie.getValue();
                    var subject = tokenService.validateToken(token);

                    Usuario usuario = repository.findByEmail(subject);
                    if (usuario == null) {
                        throw new RuntimeException("Usuário não encontrado para o token fornecido.");
                    }

                    UserDetails userDetails = new UserDetailsImpl(usuario);
                    var auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(auth);

                    tokenService.salvarTokenCookie(usuario, response);

                    return Optional.of(response(usuario));
                }
            }
        }

        return Optional.empty();
    }

    public UsuarioResponseDTO response(Usuario usuario) {

        String encryptedId;
        try {
            encryptedId = AESUtil.encrypt(usuario.getId());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao criptografar o ID do usuário", e);
        }

        UsuarioResponseDTO responseDTO = UsuarioResponseDTO.builder()
                .id(encryptedId)
                .email(usuario.getEmail())
                .nome(usuario.getNome())
                .role(usuario.getRole()).build();

        return responseDTO;
    }

}
