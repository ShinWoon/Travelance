package com.easyone.travelance.domain.account.controller;

import com.easyone.travelance.domain.account.dto.SelectedRequestDto;
import com.easyone.travelance.domain.account.service.AccountService;
import com.easyone.travelance.domain.member.entity.MainAccount;
import com.easyone.travelance.domain.member.entity.Member;
import com.easyone.travelance.domain.member.service.MemberService;
import com.easyone.travelance.global.memberInfo.MemberInfo;
import com.easyone.travelance.global.memberInfo.MemberInfoDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/account")
@Slf4j
public class AccountController {

    private final MemberService memberService;
    private final AccountService accountService;

    @Operation(summary = "전체계좌 조회", description = "나의 모든 계좌를 조회하는 메서드 입니다")
    @PostMapping("/allaccount")
    public Mono<ResponseEntity<List<Object>>> allAccount(@MemberInfo MemberInfoDto memberInfoDto) {
        Member member = memberService.findMemberByEmail(memberInfoDto.getEmail());

        return accountService.allAccount(member.getPrivateId())
                .collectList() // Flux를 Mono<List<Object>>로 변환합니다.
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND)); // 만약 데이터가 없을 경우의 처리
    }

    @Operation(summary = "계좌 목록 DB 저장", description = " 내가 선택한 계좌를 DB에 저장하는 메서드 입니다\n\n" + "{\n\n" +
            "    {\n" + "      \"account\": \"7753621811018015\",\n" + "      \"bankName\": \"SC제일은행\",\n" + "      \"accountUrl\": \"img/bank/001_SC제일은행\"\n" + "    },    \n" + "    {\n" + "      \"account\": \"7753621811018015\",\n" + "      \"bankName\": \"SC제일은행\",\n" + "      \"accountUrl\": \"img/bank/001_SC제일은행\"\n" + "    },    \n\n"+ "}\n\n" + "이런 형식으로 넣으시면 됩니다.")
    @PostMapping("/selectedaccount")
    public ResponseEntity<String> SaveAccount(@RequestBody List<SelectedRequestDto> selectedRequestDtoList, @MemberInfo MemberInfoDto memberInfoDto) {
        Member member = memberService.findMemberByEmail(memberInfoDto.getEmail());
        MainAccount mainAccount = member.getMainAccount();
        log.warn("mainAccount : " + mainAccount);
        log.warn("Received JSON data: " + selectedRequestDtoList);
        for (SelectedRequestDto selectedRequestDto : selectedRequestDtoList) {
            accountService.SaveAccount(mainAccount, selectedRequestDto);
        }

        return ResponseEntity.ok("success");
    }
}
