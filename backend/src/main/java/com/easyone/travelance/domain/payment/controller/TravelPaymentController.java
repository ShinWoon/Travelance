package com.easyone.travelance.domain.payment.controller;

import com.easyone.travelance.domain.member.entity.Member;
import com.easyone.travelance.domain.member.service.MemberService;
import com.easyone.travelance.domain.payment.dto.TravelPaymentRequestDto;
import com.easyone.travelance.domain.payment.dto.TravelPaymentResponseDto;
import com.easyone.travelance.domain.payment.service.TravelPaymentWithService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<TravelPaymentResponseDto>> getPaymentWith(@RequestParam String email) {

        Member member = memberService.findMemberByEmail(email);
        List<TravelPaymentResponseDto> travelPaymentResponseDtoList = travelPaymentWithService.getPaymentWith(member);

        return new ResponseEntity<>(travelPaymentResponseDtoList, HttpStatus.OK);
    }

    @GetMapping(value = "/alone")
    @Operation(summary = "내가 결제한 금액 중, 개인 결제 내역만 가져옵니다.")
    public ResponseEntity<List<TravelPaymentResponseDto>> getPaymentAlone(@RequestParam String email) {

        Member member = memberService.findMemberByEmail(email);
        List<TravelPaymentResponseDto> travelPaymentResponseDtoList = travelPaymentWithService.getPaymentAlone(member);

        return new ResponseEntity<>(travelPaymentResponseDtoList, HttpStatus.OK);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(EntityNotFoundException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
