package com.estudos.chat.kafka.dtos.response;

import lombok.Builder;

@Builder
public record KafkaResponseDTO(String method, Object object, String toTopic) {
}
