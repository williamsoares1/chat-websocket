package com.estudos.chat.model.dtos.response;

import com.estudos.chat.model.enums.USERROLE;

import lombok.Builder;

@Builder
public record UsuarioResponseDTO(String id, String nome, String email, USERROLE role) {

}
