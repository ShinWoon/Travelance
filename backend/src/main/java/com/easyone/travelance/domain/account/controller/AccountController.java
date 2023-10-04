package com.easyone.travelance.domain.account.controller;

import com.easyone.travelance.domain.account.dto.*;
import com.easyone.travelance.domain.account.entity.Account;
import com.easyone.travelance.domain.account.service.AccountService;
import com.easyone.travelance.domain.card.dto.DeleteCardRequestDto;
import com.easyone.travelance.domain.common.ResultDto;
import com.easyone.travelance.domain.member.entity.MainAccount;
import com.easyone.travelance.domain.member.entity.Member;
import com.easyone.travelance.domain.member.respository.MainAccountRepository;
import com.easyone.travelance.domain.member.respository.MemberRepository;
import com.easyone.travelance.domain.member.service.MemberService;
import com.easyone.travelance.global.memberInfo.MemberInfo;
import com.easyone.travelance.global.memberInfo.MemberInfoDto;
import com.nimbusds.oauth2.sdk.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/account")
@Slf4j
public class AccountController {

    private final MemberService memberService;
    private final AccountService accountService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final MemberRepository memberRepository;

    @Operation(summary = "1원이체 요청", description = "요청 시 사용자의 계좌로 1원 및 랜덤으로 생성된 4자리 숫자를 보냅니다." +
            "name : 사용자이름, bankname: 은행명, account : 계좌번호 \n\n" + "### [DTO] \n\n" + "```\n\n" +
            "{\n" +
            "\n" +
            "    \"name\": \"김제준\",\n" +
            "    \"bankName\": \"대구\",\n" +
            "    \"account\": \"1990896820482055\",\n" +
            "    \"verifyCode\": \"아무거나\"\n" +
            "\n" +
            "}\n\n" + "```")
    @PostMapping(value = "/1request")
    public Mono<ResponseEntity<Object>> oneTransferMoney(@MemberInfo MemberInfoDto memberInfoDto, @RequestBody OneRequestDto oneRequestDto) {
        String name = oneRequestDto.getName();
        String bankName = oneRequestDto.getBankName();
        String account = oneRequestDto.getAccount();

        return accountService.oneTransferMoney(name, bankName, account)
                .flatMap(result -> {
                    if (result instanceof Map) {
                        Map<String, Object> resultMap = (Map<String, Object>) result;
                        if (resultMap.containsKey("verifyCode") && resultMap.get("verifyCode") == null) {
                            return Mono.error(new Exception("잘못된 응답입니다"));
                        }
                    }
                    return Mono.just(result);
                })
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .onErrorResume(e -> {
                    if (e.getMessage().equals("잘못된 응답입니다")) {
                        return Mono.just(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
                    } else {
                        return Mono.just(new ResponseEntity<>(new Exception(e.getMessage()), HttpStatus.BAD_REQUEST));
                    }
                });
    }
    @Operation(summary = "1원이체 확인", description = "1원 이체시 받은 난수와 비교 후 privateId를 반환합니다." +
            "name : 사용자이름, bankname: 은행명, account : 계좌번호, verifyCode : 난수\n\n" +
            "### [DTO] \n\n" + "```\n\n" +
            "{\n" +
            "\n" +
            "    \"name\": \"김제준\",\n" +
            "    \"bankName\": \"대구\",\n" +
            "    \"account\": \"1990896820482055\",\n" +
            "    \"verifyCode\": \"8426\"\n" +
            "\n" +
            "}\n\n" + "```")
    @PostMapping(value = "/1response")
    public Mono<ResponseEntity<ResultDto>> oneCheckMoney(@MemberInfo MemberInfoDto memberInfoDto, @RequestBody OneCheckRequestDto oneCheckRequestDto) {
        String name = oneCheckRequestDto.getName();
        String bankName = oneCheckRequestDto.getBankName();
        String account = oneCheckRequestDto.getAccount();
        String verifyCode = oneCheckRequestDto.getVerifyCode();

        return accountService.oneCheckMoney(name, bankName, account, verifyCode)
                .flatMap(result -> {
                    String externalPrivateId = (String) result; // 외부 API에서 받은 privateId

                    // 여기서는 DB를 업데이트하는 메소드를 호출합니다.
                    return accountService.updatePrivateId(memberService.findMemberByEmail(memberInfoDto.getEmail()), externalPrivateId)
                            .map(updatedResult -> {
                                ResultDto response = new ResultDto("이체확인완료");
                                return new ResponseEntity<>(response, HttpStatus.OK);
                            });
                })
                .defaultIfEmpty(new ResponseEntity<>(new ResultDto("데이터가 없음"), HttpStatus.OK)); // 만약 데이터가 없을 경우의 처리
    }

    @Operation(summary = "주 계좌 잔액 조회", description = "주 계좌의 잔액을 조회합니다.")
    @PostMapping(value = "/search/balance")
    public Mono<ResponseEntity<Object>> searchBalance(@MemberInfo MemberInfoDto memberInfoDto, @RequestBody BalanceRequestDto balanceRequestDto) {
        // 로그인한 사람
        Member member = memberService.findMemberByEmail(memberInfoDto.getEmail());

        return accountService.searchBalance(member, balanceRequestDto)
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND)); // 만약 데이터가 없을 경우의 처리
    }

    @Operation(summary = "계좌 이체", description = "요청시 계좌이체가 실행됩니다." + "\n\n### [DTO] \n\n" + "```\n\n {\n" +
            "    \"password\"(사용자 비밀번호): \"1234\",\n" +
            "    \"depositNumber\"(내 계좌): \"기입 금지(로그인한 사용자의 주 계좌 들고옴)\",\n" +
            "    \"amount\"(보낼 금액): 2000,\n" +
            "    \"memo\": \"테스트\",\n" +
            "    \"transferAt\": \"2023-09-21T07:45:07.519Z\",\n" +
            "    \"withdrawalNumber\"(받는 사람): \"6307027645158882\"\n" +
            "}\n\n```\n\n")
    @PostMapping(value = "/transfer")
    public Mono<ResponseEntity<String>> transferAccount(@MemberInfo MemberInfoDto memberInfoDto, @RequestBody TransferRequestDto transferRequestDto) {
        // 로그인한 사람
        Member member = memberService.findMemberByEmail(memberInfoDto.getEmail());
        // 주거래계좌
        String account = member.getMainAccount().getOneAccount();

        // 내 비밀번호
        String myPassword = member.getPassword();
        // 요청받은 비밀번호
        String requestPassword = transferRequestDto.getPassword();
        log.info(myPassword + " and " + requestPassword);
        log.info(String.valueOf(bCryptPasswordEncoder.matches(requestPassword, myPassword)));

        if (bCryptPasswordEncoder.matches(requestPassword, myPassword)) {
            return accountService.transferAccount(account, transferRequestDto)
                    .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                    .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND)); // 만약 데이터가 없을 경우의 처리
        } else {
            throw new RuntimeException("비밀번호가 일치하지 않습니다");
        }


    }


    @Operation(summary = "전체계좌 조회", description = "나의 모든 계좌를 조회하는 메서드 입니다")
    @PostMapping("/allaccount")
    public Mono<ResponseEntity<List<Object>>> allAccount(@MemberInfo MemberInfoDto memberInfoDto) {
        Member member = memberService.findMemberByEmail(memberInfoDto.getEmail());

        return accountService.allAccount(member.getPrivateId())
                .collectList() // Flux를 Mono<List<Object>>로 변환합니다.
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND)); // 만약 데이터가 없을 경우의 처리
    }

    @Operation(summary = "계좌 목록 DB 저장", description = " 내가 선택한 계좌를 DB에 저장하는 메서드 입니다\n\n" + "``` \n\n" + "[\n\n" +
            "    {\n" + "      \"account\": \"6666666666666666\",\n" + "      \"bankName\": \"대구\",\n" + "      \"idx\": 4\n" + "    },    \n" + "    {\n" + "      \"account\": \"7753621811018015\",\n" + "      \"bankName\": \"SC제일\",\n" + "      \"idx\": 0\n" + "    }    \n\n" + "]\n\n ``` \n\n" + "이런 형식으로 넣으시면 됩니다.")
    @PostMapping("/selectedaccount")
    public ResponseEntity<ResultDto> SaveAccount(@RequestBody List<SelectedAccountRequestDto> selectedAccountRequestDtoList, @MemberInfo MemberInfoDto memberInfoDto) {
        Member member = memberService.findMemberByEmail(memberInfoDto.getEmail());
        MainAccount mainAccount = member.getMainAccount();
        log.warn("mainAccount : " + mainAccount);
        log.warn("Received JSON data: " + selectedAccountRequestDtoList);
        for (SelectedAccountRequestDto selectedAccountRequestDto : selectedAccountRequestDtoList) {
            accountService.SaveAccount(member,mainAccount, selectedAccountRequestDto);
        }
        ResultDto resultDto = new ResultDto("저장 성공");
        return ResponseEntity.ok(resultDto);
    }

    @Operation(summary = "계좌 삭제", description = "현재 로그인한 유저의 등록된 계좌를 삭제하는 메서드입니다.")
    @DeleteMapping("/delete/{accountName}/{account}")
//    @CacheEvict(value = "accountCache", key = "#memberInfoDto.email")
    public ResponseEntity<ResultDto> deleteAccount(
            @MemberInfo MemberInfoDto memberInfoDto,
            @PathVariable String accountName,
            @PathVariable String account
    ) {
        Member member = memberService.findMemberByEmail(memberInfoDto.getEmail());
        List<Account> accountList = member.getMainAccount().getAccountList();

        log.info("accountList : " + accountList);

        Iterator<Account> iterator = accountList.iterator();
        boolean trigger = false;

        while (iterator.hasNext()) {
            Account currentAccount = iterator.next();
            if (account.equals(currentAccount.getAccount()) && accountName.equals(currentAccount.getAccountName())) {
                iterator.remove(); // 안전하게 삭제
                trigger = true;
                break;
            }
        }
        if (trigger) {
            member.getMainAccount().setAccountList(accountList);
            memberRepository.save(member);
            if(account.equals(member.getMainAccount().getOneAccount())){
                member.getMainAccount().setOneAccount(null);
                memberRepository.save(member);
                return ResponseEntity.ok(new ResultDto("계좌 삭제 성공/ 주 계좌 null로 설정"));
            }
            else{
                return ResponseEntity.ok(new ResultDto("계좌 삭제 성공"));
            }
        } else {
            return ResponseEntity.ok(new ResultDto("계좌 삭제 실패"));
        }
    }
}
