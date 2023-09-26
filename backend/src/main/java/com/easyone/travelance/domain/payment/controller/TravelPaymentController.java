package com.easyone.travelance.domain.payment.controller;

import com.easyone.travelance.domain.common.ResultDto;
import com.easyone.travelance.domain.member.entity.Member;
import com.easyone.travelance.domain.member.service.MemberService;
import com.easyone.travelance.domain.payment.entity.Payment;
import com.easyone.travelance.domain.payment.service.TravelPaymentWithService;
import com.easyone.travelance.domain.travel.dto.RoomAllResponseDto;
import com.easyone.travelance.global.memberInfo.MemberInfo;
import com.easyone.travelance.global.memberInfo.MemberInfoDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/travel/payment")
public class TravelPaymentController {

    private final MemberService memberService;
    private final TravelPaymentWithService travelPaymentWithService;

    @GetMapping(value = "/with")
    @Operation(summary = "내가 결제한 금액 중, 공금인 내역만 가져옵니다.")
    public ResponseEntity<List<Payment>> getPaymentWith(@MemberInfo MemberInfoDto memberInfo) {

        Member member = memberService.findMemberByEmail(memberInfo.getEmail());
        List<Payment> payments = travelPaymentWithService.getPaymentWith(member);

        return new ResponseEntity<>(payments, HttpStatus.OK);
    }

    @GetMapping(value = "/alone")
    @Operation(summary = "내가 결제한 금액 중, 공금인 내역만 가져옵니다.")
    public ResponseEntity<List<Payment>> getPaymentAlone(@MemberInfo MemberInfoDto memberInfo) {

        Member member = memberService.findMemberByEmail(memberInfo.getEmail());
        List<Payment> payments = travelPaymentWithService.getPaymentAlone(member);

        return new ResponseEntity<>(payments, HttpStatus.OK);
    }


    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(EntityNotFoundException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
