package com.estudos.chat.model.dtos.request;

import java.util.List;

import lombok.Builder;

@Builder
public record ChatCreateRequestDTO(String nome, List<String> usuariosId) {

}
