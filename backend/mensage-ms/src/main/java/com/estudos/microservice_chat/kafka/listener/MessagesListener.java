package com.estudos.microservice_chat.kafka.listener;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.estudos.microservice_chat.kafka.dtos.response.KafkaResponseDTO;

@Component
@ConditionalOnProperty(name = "kafka.enabled", havingValue = "true", matchIfMissing = true)
public class MessagesListener {

    @KafkaListener(topics = "Messages", groupId = "websocketpj", containerFactory = "containerFactory")

    public void listener(@Payload KafkaResponseDTO response) {

    }
}
