package com.easyone.travelance.domain.card.dto;

import lombok.Data;

@Data
public class SelectedCardRequestDto {
    private String cardNumber;
    private String cardCoName;
    private Long idx;
    private String cardCoCode;

    // Jackson 역직렬화를 위한 기본 생성자 추가
    public SelectedCardRequestDto() {}

    public SelectedCardRequestDto(String cardNumber, String cardCoName, Long idx, String cardCoCode){
        this.cardNumber = cardNumber;
        this.cardCoName =cardCoName;
        this.idx= idx;
        this.cardCoCode = cardCoCode;

    }
}
