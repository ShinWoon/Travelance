package com.easyone.travelance.domain.account.service;

import com.easyone.travelance.domain.account.dto.AllAccountRequestDto;
import com.easyone.travelance.domain.account.dto.SelectedRequestDto;
import com.easyone.travelance.domain.account.entity.Account;
import com.easyone.travelance.domain.account.respository.AccountRepository;
import com.easyone.travelance.domain.member.entity.MainAccount;
import com.easyone.travelance.domain.member.entity.Member;
import com.easyone.travelance.domain.member.respository.MainAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AccountService {

    @Value("${http.auth-token-header.name}")
    private String authTokenHeaderName;

    @Value("${http.auth-token}")
    private String authToken;

    @Autowired
    private final WebClient.Builder webClientBuilder;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private MainAccountRepository mainAccountRepository;

    public Flux<Object> allAccount(String privateId) {
        return webClientBuilder.baseUrl("http://localhost:8081") // API 엔드포인트를 설정합니다.
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(authTokenHeaderName, authToken)
                .build()
                .post()
                .uri("/bank/account/search/account")
                .bodyValue(new AllAccountRequestDto(privateId)) // 요청 바디에 데이터를 설정합니다.
                .retrieve()
                .bodyToFlux(Object.class); // 응답을 Flux<Object> 형태로 받습니다.
    }


    public void SaveAccount(MainAccount mainAccount, SelectedRequestDto selectedRequestDto) {
        Account account = Account.builder()
                .account(selectedRequestDto.getAccount())
                .accountName(selectedRequestDto.getBankName())  // 수정된 부분
                .accountUrl(selectedRequestDto.getAccountUrl())  // 수정된 부분
                .mainAccount(mainAccount)
                .build();

        accountRepository.save(account);
        log.error("mainAccount : " + mainAccount);

        mainAccount.getAccountList().add(account);
        mainAccountRepository.save(mainAccount);
    }
}



