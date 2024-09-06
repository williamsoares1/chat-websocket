package com.estudos.chat.domain.dtos.request;

import lombok.Builder;

@Builder
public record MessageDTO(String userId, String message) {

}
