package com.easyone.travelance.domain.card.controller;

import com.easyone.travelance.domain.account.service.AccountService;
import com.easyone.travelance.domain.card.service.CardService;
import com.easyone.travelance.domain.member.entity.Member;
import com.easyone.travelance.domain.member.service.MemberService;
import com.easyone.travelance.global.memberInfo.MemberInfo;
import com.easyone.travelance.global.memberInfo.MemberInfoDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/card")
public class CardController {

    private final MemberService memberService;
    private final CardService cardService;

    @Operation(summary = "전체카드 조회", description = "나의 모든 카드를 조회하는 메서드 입니다")
    @PostMapping("/allcard")
    public Mono<ResponseEntity<List<Object>>> allCard(@MemberInfo MemberInfoDto memberInfoDto){
        Member member = memberService.findMemberByEmail(memberInfoDto.getEmail());

        return cardService.allCard(member.getPrivateId())
                .collectList() // Flux를 Mono<List<Object>>로 변환합니다.
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND)); // 만약 데이터가 없을 경우의 처리
    }
}
