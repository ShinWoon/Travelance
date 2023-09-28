package com.easyone.travelance.domain.card.controller;

import com.easyone.travelance.domain.account.dto.SelectedAccountRequestDto;
import com.easyone.travelance.domain.card.dto.SelectedCardRequestDto;
import com.easyone.travelance.domain.card.service.CardService;
import com.easyone.travelance.domain.common.ResultDto;
import com.easyone.travelance.domain.member.entity.MainAccount;
import com.easyone.travelance.domain.member.entity.Member;
import com.easyone.travelance.domain.member.service.MemberService;
import com.easyone.travelance.global.memberInfo.MemberInfo;
import com.easyone.travelance.global.memberInfo.MemberInfoDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
}
