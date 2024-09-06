package com.estudos.microservice_chat.kafka.listener;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.estudos.microservice_chat.kafka.dtos.response.KafkaResponseDTO;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
@ConditionalOnProperty(name = "kafka.enabled", havingValue = "true", matchIfMissing = true)
public class UsuarioListener {

    @KafkaListener(topics = "Users", groupId = "websocketpj", containerFactory = "containerFactory")

    public void listener(@Payload KafkaResponseDTO response) {
        log.info(":::::KAFKA: " + response);
    }

}
