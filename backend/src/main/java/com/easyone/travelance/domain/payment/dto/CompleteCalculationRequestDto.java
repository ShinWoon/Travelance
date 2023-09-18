package com.easyone.travelance.domain.payment.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.threeten.bp.LocalDateTime;

import java.util.List;

@Data
@NoArgsConstructor
public class CompleteCalculationRequestDto {
    private Long roomNumber;
    private Long memberId;
    private List<PaymentWith> paymentWithList;

    public static class PaymentWith{
        private LocalDateTime paymentAt;
        private Long paymentAmount;
        private String paymentContent;
        private Long approvalNumber;
        private String storeAddress;
        private String storeSector;
    }
}
