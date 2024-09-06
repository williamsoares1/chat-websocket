package com.estudos.domain_ms.dtos.request;

import lombok.Builder;

@Builder
public record UsuarioLoginRequestDTO(String email, String senha) {

}
