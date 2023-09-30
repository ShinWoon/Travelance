package com.easyone.travelance.domain.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterCashRequestDto {
    private Long roomNumber;
    private String paymentContent;
    private Long paymentAmount;
}
