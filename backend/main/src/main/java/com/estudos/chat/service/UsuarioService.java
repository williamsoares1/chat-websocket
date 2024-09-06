package com.estudos.chat.service;

import java.util.Optional;

import com.estudos.domain_ms.dtos.request.UsuarioLoginRequestDTO;
import com.estudos.domain_ms.dtos.request.UsuarioRegisterRequestDTO;
import com.estudos.domain_ms.dtos.response.UsuarioResponseDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

public interface UsuarioService {
    Optional<UsuarioResponseDTO> login(UsuarioLoginRequestDTO dto, HttpServletRequest request, HttpServletResponse response);
    Optional<String> register(@Valid UsuarioRegisterRequestDTO dto);
    Optional<UsuarioResponseDTO> obterRefreshToken(HttpServletRequest request, HttpServletResponse response);
}