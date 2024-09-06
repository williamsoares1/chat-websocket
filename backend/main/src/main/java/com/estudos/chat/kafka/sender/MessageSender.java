package com.estudos.chat.kafka.sender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.estudos.chat.kafka.dtos.response.KafkaResponseDTO;
import com.estudos.domain_ms.model.Message;

@Component
@ConditionalOnProperty(name = "kafka.enabled", havingValue = "true", matchIfMissing = true)
public class MessageSender {

    @Autowired
    private KafkaTemplate<String, KafkaResponseDTO> kafkaTemplate;

    public void novoPedido(Message doc) {
        kafkaTemplate.send("Messages", KafkaResponseDTO.builder().method("POST").object(doc).toTopic("Messages").build());
    }

}
