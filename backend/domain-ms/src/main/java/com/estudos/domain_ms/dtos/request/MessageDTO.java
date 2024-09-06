package com.estudos.domain_ms.dtos.request;

import lombok.Builder;

@Builder
public record MessageDTO(String userId, String message) {

}
