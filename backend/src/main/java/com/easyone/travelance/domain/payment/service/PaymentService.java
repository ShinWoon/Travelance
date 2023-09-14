package com.easyone.travelance.domain.payment.service;

import com.easyone.travelance.domain.payment.dto.CompleteCalculationRequestDto;

public interface PaymentService {

    String completeCalculation(CompleteCalculationRequestDto completeCalculationRequestDto);
}
