package com.easyone.travelance.domain.payment.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PushAlertRequestDto {
    private Long memberId;
    private Long roomNumber;
    private Long paymentId;
    private String paymentAt;
    private Long paymentAmount;
    private String paymentContent;
    private String storeAddress;
    private String storeSector;
    private boolean isWithPaid;
}
