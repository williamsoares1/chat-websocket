package com.estudos.chat.domain.dtos.response;

import com.estudos.chat.domain.enums.USERROLE;

import lombok.Builder;

@Builder
public record UsuarioResponseDTO(String id, String nome, String email, USERROLE role) {

}
