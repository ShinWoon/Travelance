package com.easyone.travelance.domain.card.service;

import com.easyone.travelance.domain.account.dto.AllAccountRequestDto;
import com.easyone.travelance.domain.card.dto.SelectedCardRequestDto;
import com.easyone.travelance.domain.card.entity.Card;
import com.easyone.travelance.domain.card.respository.CardRepository;
import com.easyone.travelance.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CardService {

    @Autowired
    private WebClient webClient;

    @Autowired
    private final CardRepository cardRepository;

    public Flux<Object> allCard(String privateId) {
        return webClient // API 엔드포인트를 설정합니다.
                .post()
                .uri("/card/list")
                .bodyValue(new AllAccountRequestDto(privateId)) // 요청 바디에 데이터를 설정합니다.
                .retrieve()
                .bodyToFlux(Object.class); // 응답을 Flux<Object> 형태로 받습니다.
    }

    // 특정 카드들 등록
//    @CacheEvict(value = "cardCache", key = "#member.email")
    public void SaveCard(Member member, SelectedCardRequestDto selectedCardRequestDto) {
        String cardNumber = selectedCardRequestDto.getCardNumber();

        // 중복된 카드 정보가 있는지 확인
        Optional<Card> existingCard = cardRepository.findByCardNumber(cardNumber);

        if (existingCard.isEmpty()) {
            Card card = Card.builder()
                    .cardCoName(selectedCardRequestDto.getCardCoName())
                    .cardNickname(selectedCardRequestDto.getCardCoName())
                    .cardNumber(selectedCardRequestDto.getCardNumber())
                    .idx(selectedCardRequestDto.getIdx())
                    .member(member)
                    .build();


            cardRepository.save(card);        }



    }
}

