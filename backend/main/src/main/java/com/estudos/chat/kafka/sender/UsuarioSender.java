package com.estudos.chat.kafka.sender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.estudos.domain_ms.entity.Usuario;
import com.estudos.chat.kafka.dtos.response.KafkaResponseDTO;

@Component
@ConditionalOnProperty(name = "kafka.enabled", havingValue = "true", matchIfMissing = true)
public class UsuarioSender {

    @Autowired
    private KafkaTemplate<String, KafkaResponseDTO> kafkaTemplate;

    public void register(Usuario entity) {
        kafkaTemplate.send("Users", KafkaResponseDTO.builder().method("POST").object(entity).toTopic("Users").build());
    }
}
