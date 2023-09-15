package com.easyone.travelance.domain.payment.service;

import com.easyone.travelance.domain.payment.dto.CompleteCalculationRequestDto;
import com.easyone.travelance.domain.payment.dto.RegisterCashRequestDto;

public interface PaymentService {

    String registerCash(RegisterCashRequestDto registerCashRequestDto);
    String completeCalculation(CompleteCalculationRequestDto completeCalculationRequestDto);
}
