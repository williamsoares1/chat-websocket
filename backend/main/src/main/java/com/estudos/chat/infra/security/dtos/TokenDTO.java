package com.estudos.chat.infra.security.dtos;

import lombok.Builder;

@Builder
public record TokenDTO(String token, String refreshToken) {

}
