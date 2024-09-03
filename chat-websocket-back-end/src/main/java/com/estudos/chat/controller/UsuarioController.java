package com.estudos.chat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estudos.chat.domain.entity.Usuario;
import com.estudos.chat.service.UsuarioService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @PostMapping("/login")
    public ResponseEntity<Usuario> login(@RequestBody Usuario dto, HttpServletRequest request){
        return ResponseEntity.of(service.login(dto, request));
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody Usuario dto){
        try{
            return ResponseEntity.ok(service.register(dto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro :" + e.getMessage());
        }
    }
}
