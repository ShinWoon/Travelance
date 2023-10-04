package com.easyone.travelance.domain.payment.service;

import com.easyone.travelance.domain.member.entity.Member;
import com.easyone.travelance.domain.payment.dto.*;
import com.easyone.travelance.global.memberInfo.MemberInfoDto;

public interface PaymentService {

    String registerCash(Member member, RegisterCashRequestDto registerCashRequestDto);
    String pushAlertData(PushAlertRequestDto pushAlertRequestDto);
    String transferAccount(Member member, TransferAccountRequestDto transferAccountRequestDto);
    String completeCalculation(MemberInfoDto memberInfoDto, CompleteCalculationRequestDto completeCalculationRequestDto);

    TransferInfoDto getTransferInfo(Long roomId, Member member);
}
