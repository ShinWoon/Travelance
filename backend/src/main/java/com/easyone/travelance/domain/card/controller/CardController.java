package com.easyone.travelance.domain.card.controller;

import com.easyone.travelance.domain.account.entity.Account;
import com.easyone.travelance.domain.card.dto.DeleteCardRequestDto;
import com.easyone.travelance.domain.card.dto.SelectedCardRequestDto;
import com.easyone.travelance.domain.card.entity.Card;
import com.easyone.travelance.domain.card.respository.CardRepository;
import com.easyone.travelance.domain.card.service.CardService;
import com.easyone.travelance.domain.common.ResultDto;
import com.easyone.travelance.domain.member.entity.Member;
import com.easyone.travelance.domain.member.respository.MemberRepository;
import com.easyone.travelance.domain.member.service.MemberService;
import com.easyone.travelance.global.memberInfo.MemberInfo;
import com.easyone.travelance.global.memberInfo.MemberInfoDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Iterator;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/card")
@Slf4j
public class CardController {

    private final MemberService memberService;
    private final CardService cardService;

    private final MemberRepository memberRepository;

    @Operation(summary = "전체카드 조회", description = "나의 모든 카드를 조회하는 메서드 입니다")
    @PostMapping("/allcard")
    public Mono<ResponseEntity<List<Object>>> allCard(@MemberInfo MemberInfoDto memberInfoDto){
        Member member = memberService.findMemberByEmail(memberInfoDto.getEmail());

        return cardService.allCard(member.getPrivateId())
                .collectList() // Flux를 Mono<List<Object>>로 변환합니다.
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND)); // 만약 데이터가 없을 경우의 처리
    }

    @Operation(summary = "카드 목록 DB 저장", description = " 내가 선택한 카드를 DB에 저장하는 메서드 입니다\n\n" +
    "[\n\n" +
            "  {\n\n" +
            "    \"cardNumber\": \"4215154824392854\",\n" +
            "    \"cardCoName\": \"KB국민\",\n" +
            "    \"idx\": 1\n" +
            "  },\n\n" +
            "  {\n\n" +
            "    \"cardNumber\": \"3737467972008765\",\n" +
            "    \"cardCoName\": \"KB국민\",\n" +
            "    \"idx\": 1\n" +
            "  }\n\n" +
            "]\n\n" + "이런 형식으로 넣으시면 됩니다.")
    @PostMapping("/selectedcard")
    public ResponseEntity<ResultDto> SaveCard(@RequestBody List<SelectedCardRequestDto> selectedCardRequestDtoList, @MemberInfo MemberInfoDto memberInfoDto) {
        Member member = memberService.findMemberByEmail(memberInfoDto.getEmail());

        for (SelectedCardRequestDto selectedCardRequestDto : selectedCardRequestDtoList) {
            cardService.SaveCard(member,selectedCardRequestDto);
        }

        ResultDto resultDto = new ResultDto("저장 성공");
        return ResponseEntity.ok(resultDto);

    }

    @Operation(summary = "카드 삭제", description = "현재 로그인한 유저의 등록된 카드를 삭제하는 메서드입니다.")
    @DeleteMapping("/delete/{cardCoName}/{cardNumber}")
//    @CacheEvict(value = "cardCache", key = "#memberInfoDto.email")
    public ResponseEntity<ResultDto> deleteCard(
            @MemberInfo MemberInfoDto memberInfoDto,
            @PathVariable String cardCoName,
            @PathVariable String cardNumber
    ) {
        Member member = memberService.findMemberByEmail(memberInfoDto.getEmail());
        List<Card> cardList = member.getCardList();

        log.info("cardList : " + cardList);

        boolean trigger = false;
        Iterator<Card> iterator = cardList.iterator();

        while (iterator.hasNext()) {
            Card currentCard = iterator.next();
            if (cardNumber.equals(currentCard.getCardNumber()) && cardCoName.equals(currentCard.getCardCoName())) {
                iterator.remove(); // 안전하게 삭제
                trigger = true;
                break;
            }
        }

        if (trigger) {
            member.setCardList(cardList);
            memberRepository.save(member);
            return ResponseEntity.ok(new ResultDto("카드 삭제 성공"));
        } else {
            return ResponseEntity.ok(new ResultDto("카드 삭제 실패"));
        }
    }
}
