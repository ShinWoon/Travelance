package com.easyone.travelance.domain.payment.controller;

import com.easyone.travelance.domain.account.service.AccountService;
import com.easyone.travelance.domain.common.ResultDto;
import com.easyone.travelance.domain.member.entity.Member;
import com.easyone.travelance.domain.member.service.MemberService;
import com.easyone.travelance.domain.payment.dto.*;
import com.easyone.travelance.domain.payment.service.PaymentService;
import com.easyone.travelance.global.memberInfo.MemberInfo;
import com.easyone.travelance.global.memberInfo.MemberInfoDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;
    private final MemberService memberService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/cash")
    @Operation(summary = "현금 사용내역 등록")
    public ResponseEntity<ResultDto> registerCash(@MemberInfo MemberInfoDto memberInfoDto,
                                                  @RequestBody RegisterCashRequestDto registerCashRequestDto) {

        // 시작 시간 기록
        long startTime = System.currentTimeMillis();

        Member member = memberService.findMemberByEmail(memberInfoDto.getEmail());
        String response = paymentService.registerCash(member, registerCashRequestDto);
        ResultDto resultDto = new ResultDto(response);

        // 종료 시간 기록
        long endTime = System.currentTimeMillis();

        // 실행 시간 계산
        long executionTime = endTime - startTime;

        log.info("registerCash : " + executionTime);

        return new ResponseEntity<>(resultDto, HttpStatus.OK);
    }

    @PostMapping("/push/alert")
    @Operation(summary = "푸시알림 공금 유무 선택 후 데이터 서버로 전송", description = "공금일 때에만 boolean값을 true로 변경 후 API 요청")
    public ResponseEntity<ResultDto> pushAlertData(@RequestBody PushAlertRequestDto pushAlertRequestDto) {
        String response = paymentService.pushAlertData(pushAlertRequestDto);
        ResultDto resultDto = new ResultDto(response);
        return new ResponseEntity<>(resultDto, HttpStatus.OK);
    }

    @PostMapping("/complete")
    @Operation(summary = "정산완료 확인", description = "여행 방에 있는 인원 모두가 정산완료를 누르면 push 알림 전송")
    public ResponseEntity<ResultDto> completeCalculation(@MemberInfo MemberInfoDto memberInfoDto,
                                                         @RequestBody CompleteCalculationRequestDto completeCalculationRequestDto) {
        String response = paymentService.completeCalculation(memberInfoDto, completeCalculationRequestDto);
        ResultDto resultDto = new ResultDto(response);
        return new ResponseEntity<>(resultDto, HttpStatus.OK);
    }

    @PostMapping("/transfer")
    @Operation(summary = "계좌이체요청")
    public ResponseEntity<ResultDto> transferAccount(@MemberInfo MemberInfoDto memberInfoDto,
                                                     @RequestBody TransferAccountRequestDto transferAccountRequestDto) {
        Member member = memberService.findMemberByEmail(memberInfoDto.getEmail());
        // 내 비밀번호
        String myPassword = member.getPassword();
        // 요청받은 비밀번호
        String requestPassword = transferAccountRequestDto.getPassword();
        log.info(myPassword + " and " + requestPassword );
        log.info(String.valueOf(bCryptPasswordEncoder.matches(requestPassword, myPassword)));

        if (bCryptPasswordEncoder.matches(requestPassword, myPassword)){
            String response = paymentService.transferAccount(member, transferAccountRequestDto);
            ResultDto resultDto = new ResultDto(response);
            return new ResponseEntity<>(resultDto, HttpStatus.OK);
        }
        else{
            throw new RuntimeException("비밀번호가 일치하지 않습니다");
        }

    }

    @GetMapping("/{roomId}/info")
    @Operation(summary = "공금 관련 계좌이체 정보 제공")
    public ResponseEntity<TransferInfoDto> transferInfo(@PathVariable Long roomId,
                                                        @MemberInfo MemberInfoDto memberInfoDto){
        Member member = memberService.findMemberByEmail(memberInfoDto.getEmail());
        TransferInfoDto transferInfoDto = paymentService.getTransferInfo(roomId, member);

        return new ResponseEntity<>(transferInfoDto, HttpStatus.OK);

    }


    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(EntityNotFoundException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
