package com.easyone.travelance;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

@SpringBootApplication (exclude = SecurityAutoConfiguration.class)
@EnableCaching
public class TravelanceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TravelanceApplication.class, args);
	}

//	@Bean
//	public ObjectMapper objectMapper() {
//		PolymorphicTypeValidator ptv = BasicPolymorphicTypeValidator
//				.builder()
//				.allowIfSubType(Object.class)
//				.build();
//		ObjectMapper mapper = new ObjectMapper();
//		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
//		mapper.registerModule(new JavaTimeModule());
//		mapper.activateDefaultTyping(ptv, ObjectMapper.DefaultTyping.NON_FINAL);
//		return mapper;
//	}


}
