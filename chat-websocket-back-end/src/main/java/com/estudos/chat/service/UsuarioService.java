package com.estudos.chat.service;

import java.util.Optional;

import org.springframework.http.ProblemDetail;
import org.springframework.stereotype.Service;

import com.estudos.chat.infra.security.dtos.TokenDTO;
import com.estudos.chat.infra.security.dtos.TokenRefreshDTO;
import com.estudos.chat.model.dtos.request.UsuarioCadastroRequestDTO;
import com.estudos.chat.model.dtos.request.UsuarioLoginRequestDTO;
import com.estudos.chat.model.dtos.response.UsuarioResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

public interface UsuarioService {
    Optional<UsuarioResponseDTO> login(UsuarioLoginRequestDTO dto, HttpServletRequest request, HttpServletResponse response);
    Optional<String> register(@Valid UsuarioCadastroRequestDTO dto);
    Optional<UsuarioResponseDTO> obterRefreshToken(HttpServletRequest request, HttpServletResponse response);
}