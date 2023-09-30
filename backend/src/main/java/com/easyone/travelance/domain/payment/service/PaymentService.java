package com.easyone.travelance.domain.payment.service;

import com.easyone.travelance.domain.member.entity.Member;
import com.easyone.travelance.domain.payment.dto.CompleteCalculationRequestDto;
import com.easyone.travelance.domain.payment.dto.PushAlertRequestDto;
import com.easyone.travelance.domain.payment.dto.RegisterCashRequestDto;
import com.easyone.travelance.domain.payment.dto.TransferAccountRequestDto;

public interface PaymentService {

    String registerCash(Member member, RegisterCashRequestDto registerCashRequestDto);
    String pushAlertData(PushAlertRequestDto pushAlertRequestDto);
    String completeCalculation(CompleteCalculationRequestDto completeCalculationRequestDto);
    String transferAccount(TransferAccountRequestDto transferAccountRequestDto);
}
