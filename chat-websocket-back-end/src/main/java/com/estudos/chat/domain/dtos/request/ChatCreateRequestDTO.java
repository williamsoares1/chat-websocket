package com.estudos.chat.domain.dtos.request;

import java.util.List;

import lombok.Builder;

@Builder
public record ChatCreateRequestDTO(String nome, List<String> usuariosId) {

}
