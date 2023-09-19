package com.easyone.travelance.domain.card.service;

import com.easyone.travelance.domain.account.dto.AllAccountRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Service
@Transactional
@RequiredArgsConstructor
public class CardService {

    @Value("${http.auth-token-header.name}")
    private String authTokenHeaderName;

    @Value("${http.auth-token}")
    private String authToken;

    @Autowired
    private final WebClient.Builder webClientBuilder;

    public Flux<Object> allCard(String privateId) {
        return webClientBuilder.baseUrl("http://localhost:8081") // API 엔드포인트를 설정합니다.
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(authTokenHeaderName, authToken)
                .build()
                .post()
                .uri("/bank/card/list")
                .bodyValue(new AllAccountRequestDto(privateId)) // 요청 바디에 데이터를 설정합니다.
                .retrieve()
                .bodyToFlux(Object.class); // 응답을 Flux<Object> 형태로 받습니다.
    }
}

