package com.easyone.travelance.global.config;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.kafka.support.serializer.JsonDeserializer;

public class CustomJsonDeserializer<T> extends JsonDeserializer<T> {
    public CustomJsonDeserializer(Class<T> targetType) {
        super(targetType);
        this.objectMapper.registerModule(new JavaTimeModule());
    }
}
