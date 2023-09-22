package com.easyone.travelance.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorResourceFactory;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public ReactorResourceFactory resourceFactory() {
        ReactorResourceFactory factory = new ReactorResourceFactory();
        factory.setUseGlobalResources(false);
        return factory;
    }

    @Bean
    public WebClient webClient(){
        return WebClient.builder()
//                .baseUrl("http://localhost:8080/bank")      // 추후 url 변경
                .baseUrl("http://3.39.110.134:8083/bank")      // 추후 url 변경
                .defaultHeader("Authorization", "ssafy_d210_bankserver")
                .build();
    }
}
