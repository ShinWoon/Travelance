package com.easyone.travelance.domain.payment.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class PaymentAlertRequestDto {
    private String memberPrivateId;
    private String paymentAt;
    private Long paymentAmount;
    private String paymentContent;
    private String storeSector;
    private String storeAddress;
}
