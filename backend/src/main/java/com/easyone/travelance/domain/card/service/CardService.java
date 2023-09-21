package com.easyone.travelance.domain.card.service;

import com.easyone.travelance.domain.account.dto.AllAccountRequestDto;
import com.easyone.travelance.domain.account.dto.SelectedAccountRequestDto;
import com.easyone.travelance.domain.account.entity.Account;
import com.easyone.travelance.domain.card.dto.SelectedCardRequestDto;
import com.easyone.travelance.domain.card.entity.Card;
import com.easyone.travelance.domain.card.respository.CardRepository;
import com.easyone.travelance.domain.member.entity.MainAccount;
import com.easyone.travelance.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.Optional;

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

    @Autowired
    private final CardRepository cardRepository;

    @Value("http://localhost:8081")
    private String Url;

    public Flux<Object> allCard(String privateId) {
        return webClientBuilder.baseUrl(Url) // API 엔드포인트를 설정합니다.
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(authTokenHeaderName, authToken)
                .build()
                .post()
                .uri("/bank/card/list")
                .bodyValue(new AllAccountRequestDto(privateId)) // 요청 바디에 데이터를 설정합니다.
                .retrieve()
                .bodyToFlux(Object.class); // 응답을 Flux<Object> 형태로 받습니다.
    }

    // 특정 카드들 등록
    public void SaveCard(Member member, SelectedCardRequestDto selectedCardRequestDto) {
        String cardNumber = selectedCardRequestDto.getCardNumber();

        // 중복된 카드 정보가 있는지 확인
        Optional<Card> existingCard = cardRepository.findByCardNumber(cardNumber);

        if (existingCard.isEmpty()) {
            Card card = Card.builder()
                    .cardCoName(selectedCardRequestDto.getCardCoName())
                    .cardNickname(selectedCardRequestDto.getCardCoName())
                    .cardNumber(selectedCardRequestDto.getCardNumber())
                    .cardLogo(selectedCardRequestDto.getCardCoLogo())
                    .color(Long.valueOf(selectedCardRequestDto.getCardCoCode()))
                    .member(member)
                    .build();


            cardRepository.save(card);        }



    }
}

