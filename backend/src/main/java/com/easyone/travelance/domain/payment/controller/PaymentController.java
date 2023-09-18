package com.easyone.travelance.domain.payment.controller;

import com.easyone.travelance.domain.payment.dto.CompleteCalculationRequestDto;
import com.easyone.travelance.domain.payment.dto.RegisterCashRequestDto;
import com.easyone.travelance.domain.payment.dto.TransferAccountRequestDto;
import com.easyone.travelance.domain.payment.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/cash")
    @Operation(summary = "현금 사용내역 등록")
    public ResponseEntity<String> registerCash(RegisterCashRequestDto registerCashRequestDto){
        String response = paymentService.registerCash(registerCashRequestDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/complete")
    @Operation(summary = "정산완료 확인", description = "여행 방에 있는 인원 모두가 정산완료를 누르면 push 알림 전송")
    public ResponseEntity<String> completeCalculation(CompleteCalculationRequestDto completeCalculationRequestDto){
        String response = paymentService.completeCalculation(completeCalculationRequestDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

//    @PostMapping("/transfer")
//    @Operation(summary = "계좌이체")
//    public ResponseEntity<String> transferAccount(TransferAccountRequestDto transferAccountRequestDto){
//        String response = paymentService.transferAccount(transferAccountRequestDto);
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(EntityNotFoundException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
