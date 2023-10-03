package com.easyone.travelance.domain.payment.service;

import com.easyone.travelance.domain.member.entity.Member;
import com.easyone.travelance.domain.payment.dto.CompleteCalculationRequestDto;
import com.easyone.travelance.domain.payment.dto.PushAlertRequestDto;
import com.easyone.travelance.domain.payment.dto.RegisterCashRequestDto;
import com.easyone.travelance.domain.payment.dto.TransferAccountRequestDto;
import com.easyone.travelance.global.memberInfo.MemberInfoDto;

public interface PaymentService {

    String registerCash(Member member, RegisterCashRequestDto registerCashRequestDto);
    String pushAlertData(PushAlertRequestDto pushAlertRequestDto);
    String transferAccount(TransferAccountRequestDto transferAccountRequestDto);
    String completeCalculation(MemberInfoDto memberInfoDto, CompleteCalculationRequestDto completeCalculationRequestDto);
}
