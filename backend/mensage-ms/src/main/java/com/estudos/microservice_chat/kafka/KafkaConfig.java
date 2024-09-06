package com.estudos.microservice_chat.kafka;

import java.time.Duration;
import java.util.HashMap;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.TopicConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.converter.JsonMessageConverter;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.util.backoff.FixedBackOff;

import com.estudos.microservice_chat.kafka.dtos.response.KafkaResponseDTO;

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
    public KafkaAdmin.NewTopics newTopics() {
        return new KafkaAdmin.NewTopics(
                TopicBuilder
                        .name("Users")
                        .partitions(2)
                        .config(TopicConfig.RETENTION_MS_CONFIG, String.valueOf(Duration.ofDays(7).toMillis()))
                        .build(),

                TopicBuilder
                        .name("Messages")
                        .partitions(2)
                        .config(TopicConfig.RETENTION_MS_CONFIG, String.valueOf(Duration.ofDays(7).toMillis()))
                        .build());
    };

    @Bean
    public ProducerFactory producer() {
        var configs = new HashMap<String, Object>();

        configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, properties.getBootstrapServers());

        configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        configs.put(ProducerConfig.ACKS_CONFIG, "1");

        configs.put(ProducerConfig.RETRIES_CONFIG, 2);

        return new DefaultKafkaProducerFactory(configs);
    }

    @Bean
    public KafkaTemplate<String, KafkaResponseDTO> jsonTemplate() {
        return new KafkaTemplate<>(producer());
    }

    private ConsumerFactory consumer() {
        var configs = new HashMap<String, Object>();

        configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, properties.getBootstrapServers());

        configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        return new DefaultKafkaConsumerFactory<>(configs);
    }

    @Bean
    ConcurrentKafkaListenerContainerFactory containerFactory() {
        var factory = new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(consumer());
        factory.setRecordMessageConverter(new JsonMessageConverter());
        factory.setCommonErrorHandler(new DefaultErrorHandler(new FixedBackOff(1000L, 3)));

        return factory;
    }
}