package com.easyone.travelance.domain.payment.controller;

import com.easyone.travelance.domain.member.entity.Member;
import com.easyone.travelance.domain.member.service.MemberService;
import com.easyone.travelance.domain.payment.dto.TravelDoneResponseDto;
import com.easyone.travelance.domain.payment.dto.TravelPaymentPlusDto;
import com.easyone.travelance.domain.payment.dto.TravelPaymentResponseDto;
import com.easyone.travelance.domain.payment.service.TravelPaymentWithService;
import com.easyone.travelance.domain.travel.dto.RoomUserResponseDto;
import com.easyone.travelance.global.memberInfo.MemberInfo;
import com.easyone.travelance.global.memberInfo.MemberInfoDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/travel/payment")
public class TravelPaymentController {

    private final MemberService memberService;
    private final TravelPaymentWithService travelPaymentWithService;

    @GetMapping(value = "/{roomId}/with")
    @Operation(summary = "내가 결제한 금액 중, 공금인 내역만 가져옵니다.")
    public ResponseEntity<TravelPaymentPlusDto> getPaymentWith(@PathVariable Long roomId,
                                                               @MemberInfo MemberInfoDto memberInfoDto) {
        // 시작 시간 기록
        long startTime = System.currentTimeMillis();

        Member member = memberService.findMemberByEmail(memberInfoDto.getEmail());
        TravelPaymentPlusDto travelPaymentPlusDto = travelPaymentWithService.getPaymentWith(roomId, member);

        // 종료 시간 기록
        long endTime = System.currentTimeMillis();

        // 실행 시간 계산
        long executionTime = endTime - startTime;

        log.info("getPaymentWith : " + executionTime);

        return new ResponseEntity<>(travelPaymentPlusDto, HttpStatus.OK);
    }

    @GetMapping(value = "/{roomId}/alone")
    @Operation(summary = "내가 결제한 금액 중, 개인 결제 내역만 가져옵니다.")
    public ResponseEntity<List<TravelPaymentResponseDto>> getPaymentAlone(@PathVariable Long roomId,
                                                                          @MemberInfo MemberInfoDto memberInfoDto) {

        // 시작 시간 기록
        long startTime = System.currentTimeMillis();

        Member member = memberService.findMemberByEmail(memberInfoDto.getEmail());
        List<TravelPaymentResponseDto> travelPaymentResponseDtoList = travelPaymentWithService.getPaymentAlone(roomId, member);


        // 종료 시간 기록
        long endTime = System.currentTimeMillis();

        // 실행 시간 계산
        long executionTime = endTime - startTime;

        log.info("getPaymentAlone : " + executionTime);


        return new ResponseEntity<>(travelPaymentResponseDtoList, HttpStatus.OK);
    }

    @Operation(summary = "여행종료 후 여행정보 조회", description = "여행 종료 후, 여행방 관련 정보를 조회합니다")
    @PostMapping(value = "/{roomId}/travelDone")
    public ResponseEntity<TravelDoneResponseDto> TravelDoneInfo(@MemberInfo MemberInfoDto memberInfoDto, @PathVariable Long roomId) {
        Member member = memberService.findMemberByEmail(memberInfoDto.getEmail());
        TravelDoneResponseDto travelDoneResponseDto = travelPaymentWithService.TravelDoneInfo(member, roomId);
        return new ResponseEntity<>(travelDoneResponseDto, HttpStatus.OK);
    }




    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(EntityNotFoundException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
