package com.easyone.travelance.domain.payment.dto;

import com.easyone.travelance.domain.payment.entity.Payment;
import lombok.Data;

@Data
public class TravelPaymentResponseDto {
    private Long id;
    private String paymentAt;
    private Long paymentAmount;
    private String paymentContent;
    private String storeAddress;
    private String storeSector;
    private Boolean isWithPaid;

    public TravelPaymentResponseDto(Payment payment) {
        this.id = payment.getId();
        this.paymentAt = payment.getPaymentAt();
        this.paymentAmount = payment.getPaymentAmount();
        this.paymentContent = payment.getPaymentContent();
        this.storeAddress = payment.getStoreAddress();
        this.storeSector = payment.getStoreSector();
        this.isWithPaid = payment.getIsWithPaid();
    }
}
