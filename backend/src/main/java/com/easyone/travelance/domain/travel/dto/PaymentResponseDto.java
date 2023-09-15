package com.easyone.travelance.domain.travel.dto;


import com.easyone.travelance.domain.member.entity.Member;
import com.easyone.travelance.domain.payment.entity.Payment;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PaymentResponseDto {
    private Long consumptionId;
    private Long price;
    private String content;
    private String category;
    private String address;
    private Long memeberId;
    private String nickname;

    public PaymentResponseDto(Payment payment, Member member) {
        this.consumptionId = payment.getId();
        this.price = payment.getPaymentAmount();
        this.address=payment.getStoreAddress();
        this.category = payment.getStoreSector();
        this.content= payment.getPaymentContent();
        this.memeberId = member.getId();
        this.nickname = member.getNickname();
    }
}
