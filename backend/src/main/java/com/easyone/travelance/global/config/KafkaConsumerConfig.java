package com.easyone.travelance.global.config;

import com.easyone.travelance.domain.payment.dto.PaymentAlertRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.SeekToCurrentErrorHandler;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.util.backoff.FixedBackOff;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {

    @Value(value = "${spring.kafka.consumer.bootstrap-servers}")
    private String bootstrapAddress;

    @Value(value = "${spring.kafka.consumer.group-id}")
    private String groupId;

    @Autowired
    private ObjectMapper objectMapper; // 필드 주입 방식

    @Bean
    public ConsumerFactory<String, PaymentAlertRequestDto> consumerFactory() {
        // CustomJsonDeserializer 사용
        CustomJsonDeserializer<PaymentAlertRequestDto> customJsonDeserializer = new CustomJsonDeserializer<>(PaymentAlertRequestDto.class);

        return new DefaultKafkaConsumerFactory<>(consumerProps(), new StringDeserializer(), customJsonDeserializer);
    }

    private Map<String, Object> consumerProps() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class.getName());
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        props.put(ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS, StringDeserializer.class);
        props.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, CustomJsonDeserializer.class.getName());

        // Add enable.auto.commit and set it to false
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);

        return props;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, PaymentAlertRequestDto> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, PaymentAlertRequestDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());

        // Set ackMode to MANUAL
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);

        // Configure the error handler to retry only once
        FixedBackOff backOff = new FixedBackOff();
        backOff.setInterval(1000L); // 1 second backoff interval
        backOff.setMaxAttempts(1); // Only retry once
        factory.setErrorHandler(new SeekToCurrentErrorHandler(backOff));

        return factory;
    }
}

