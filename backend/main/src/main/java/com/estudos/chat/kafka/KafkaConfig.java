package com.estudos.chat.kafka;

import java.util.HashMap;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.estudos.chat.kafka.dtos.response.KafkaResponseDTO;

@Configuration
@ConditionalOnProperty(name = "kafka.enabled", havingValue = "true", matchIfMissing = true)
public class KafkaConfig {

    @Autowired
    private KafkaProperties properties;

    @Bean
    public KafkaTemplate<String, KafkaResponseDTO> kafkaTemplate() {
        return new KafkaTemplate<>(producer());
    }

    @Bean
    public ProducerFactory producer() {
        var configs = new HashMap<String, Object>();

        configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, properties.getBootstrapServers());
        configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        configs.put(ProducerConfig.RETRIES_CONFIG, 3);
        configs.put(ProducerConfig.RETRY_BACKOFF_MS_CONFIG, 1000);

        return new DefaultKafkaProducerFactory(configs);
    }

    @Bean
    public KafkaTemplate<String, KafkaResponseDTO> jsonTemplate() {
        return new KafkaTemplate<>(producer());
    }

}
