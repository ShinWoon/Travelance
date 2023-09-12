package com.easyone.travelance.domain.payment.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class PaymentAlertRequestDto {
    private LocalDateTime paymentAt;
    private Long paymentAmount;
    private String paymentContent;
    private Long approvalNumber;
    private String storeAddress;
    private String storeSector;
}
