package com.estudos.chat.domain.dtos.request;

import com.estudos.chat.domain.enums.USERROLE;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public record UsuarioRegisterRequestDTO(String nome, String email, String senha, @Enumerated(EnumType.STRING) USERROLE role) {

}
