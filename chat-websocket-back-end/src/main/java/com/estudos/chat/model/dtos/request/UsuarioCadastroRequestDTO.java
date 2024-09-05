package com.estudos.chat.model.dtos.request;

import com.estudos.chat.model.enums.USERROLE;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public record UsuarioCadastroRequestDTO(String nome, String email, String senha, @Enumerated(EnumType.STRING) USERROLE role) {

}
