package com.easyone.travelance.domain.travel.dto;


import com.easyone.travelance.domain.payment.entity.Payment;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MapDetailResponseDto {
    private Long price;
    private String content;
    private String category;
    private String address;
    private String nickName;
    private String paymentAt;

    public MapDetailResponseDto(Payment payment, String nickName) {
        this.price = payment.getPaymentAmount();
        this.address=payment.getStoreAddress();
        this.category = payment.getStoreSector();
        this.content= payment.getPaymentContent();
        this.paymentAt=payment.getPaymentAt();
        this.nickName = nickName;
    }
}
