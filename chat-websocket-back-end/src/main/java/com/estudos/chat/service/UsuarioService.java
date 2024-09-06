package com.estudos.chat.service;

import java.util.Optional;

import com.estudos.chat.domain.dtos.request.UsuarioLoginRequestDTO;
import com.estudos.chat.domain.dtos.request.UsuarioRegisterRequestDTO;
import com.estudos.chat.domain.dtos.response.UsuarioResponseDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

public interface UsuarioService {
    Optional<UsuarioResponseDTO> login(UsuarioLoginRequestDTO dto, HttpServletRequest request, HttpServletResponse response);
    Optional<String> register(@Valid UsuarioRegisterRequestDTO dto);
    Optional<UsuarioResponseDTO> obterRefreshToken(HttpServletRequest request, HttpServletResponse response);
}