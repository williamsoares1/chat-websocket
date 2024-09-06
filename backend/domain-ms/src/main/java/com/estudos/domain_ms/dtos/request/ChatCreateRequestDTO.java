package com.estudos.domain_ms.dtos.request;

import java.util.List;

import lombok.Builder;

@Builder
public record ChatCreateRequestDTO(String nome, List<String> usuariosId) {

}
