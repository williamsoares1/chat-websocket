package com.estudos.chat.model.dtos.request;

import lombok.Builder;

@Builder
public record UsuarioLoginRequestDTO(String email, String senha) {

}
