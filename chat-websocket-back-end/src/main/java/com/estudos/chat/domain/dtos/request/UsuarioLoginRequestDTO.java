package com.estudos.chat.domain.dtos.request;

import lombok.Builder;

@Builder
public record UsuarioLoginRequestDTO(String email, String senha) {

}
