package com.estudos.domain_ms.dtos.response;

import com.estudos.domain_ms.enums.USERROLE;

import lombok.Builder;

@Builder
public record UsuarioResponseDTO(String id, String nome, String email, USERROLE role) {

}
