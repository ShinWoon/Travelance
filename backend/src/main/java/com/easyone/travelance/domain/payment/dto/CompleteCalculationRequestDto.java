package com.easyone.travelance.domain.payment.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CompleteCalculationRequestDto {
    private Long roomNumber;
    private List<PaymentWith> paymentWithList;

    @Data
    @NoArgsConstructor
    public static class PaymentWith{
        private Long paymentId;
        private String paymentAt;
        private Long paymentAmount;
        private String paymentContent;
        private String storeAddress;
        private String storeSector;
        private Boolean isWithPaid;
    }
}
