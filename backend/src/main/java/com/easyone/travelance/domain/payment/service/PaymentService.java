package com.easyone.travelance.domain.payment.service;

import com.easyone.travelance.domain.payment.dto.CompleteCalculationRequestDto;
import com.easyone.travelance.domain.payment.dto.RegisterCashRequestDto;
import com.easyone.travelance.domain.payment.dto.TransferAccountRequestDto;

public interface PaymentService {

    String registerCash(RegisterCashRequestDto registerCashRequestDto);
    String completeCalculation(CompleteCalculationRequestDto completeCalculationRequestDto);
//    String transferAccount(TransferAccountRequestDto transferAccountRequestDto);
}
