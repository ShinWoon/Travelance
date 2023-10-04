package com.easyone.travelance.domain.travel.dto;


import com.easyone.travelance.domain.member.entity.Member;
import com.easyone.travelance.domain.payment.entity.Payment;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PaymentResponseDto {
    private Long paymentId;
    private Long price;
    private String content;
    private String category;
    private String address;
    private Long memberId;
    private String nickName;
    private String paymentAt;
    private String profileUrl;

    public PaymentResponseDto(Payment payment, Long id, String nickName, String profileUrl) {
        this.paymentId = payment.getId();
        this.price = payment.getPaymentAmount();
        this.address=payment.getStoreAddress();
        this.category = payment.getStoreSector();
        this.content= payment.getPaymentContent();
        this.paymentAt=payment.getPaymentAt();
        this.memberId = id;
        this.nickName = nickName;
        this.profileUrl=profileUrl;
    }

}
