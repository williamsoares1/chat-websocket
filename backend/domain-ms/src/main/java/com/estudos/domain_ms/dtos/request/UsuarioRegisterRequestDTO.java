package com.estudos.domain_ms.dtos.request;

import com.estudos.domain_ms.enums.USERROLE;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public record UsuarioRegisterRequestDTO(String nome, String email, String senha, @Enumerated(EnumType.STRING) USERROLE role) {

}
