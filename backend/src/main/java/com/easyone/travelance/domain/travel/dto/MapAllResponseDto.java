package com.easyone.travelance.domain.travel.dto;


import com.easyone.travelance.domain.payment.entity.Payment;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MapAllResponseDto {
    private String paymentAt;

    public MapAllResponseDto(Payment payment) {
        paymentAt=payment.getPaymentAt();
    }
}
