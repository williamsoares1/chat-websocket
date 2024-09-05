package com.estudos.chat.infra.security.dtos;

import lombok.Builder;

@Builder
public record TokenRefreshDTO(String refreshToken) {

}
