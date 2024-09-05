package com.estudos.chat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estudos.chat.infra.security.dtos.TokenDTO;
import com.estudos.chat.infra.security.dtos.TokenRefreshDTO;
import com.estudos.chat.model.dtos.request.UsuarioCadastroRequestDTO;
import com.estudos.chat.model.dtos.request.UsuarioLoginRequestDTO;
import com.estudos.chat.model.dtos.response.UsuarioResponseDTO;
import com.estudos.chat.service.UsuarioService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/auth")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @PostMapping("/login")
    public ResponseEntity<UsuarioResponseDTO> login(@RequestBody UsuarioLoginRequestDTO dto, HttpServletRequest request, HttpServletResponse response){
        return ResponseEntity.of(service.login(dto, request, response));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<Void> refreshToken(@RequestBody TokenRefreshDTO token, HttpServletResponse response){
        return ResponseEntity.of(service.obterRefreshToken(token.refreshToken(), response));
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody UsuarioCadastroRequestDTO dto){
        try{
            return ResponseEntity.ok(service.register(dto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro :" + e.getMessage());
        }
    }

    @GetMapping("/teste")
    public String teste() {
        return "Foi";
    }
    
}
