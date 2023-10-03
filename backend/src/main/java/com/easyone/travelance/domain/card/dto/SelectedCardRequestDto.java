package com.easyone.travelance.domain.card.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SelectedCardRequestDto {
    private String cardNumber;
    private String cardCoName;
    private Long idx;



    public SelectedCardRequestDto(String cardNumber, String cardCoName, Long idx){
        this.cardNumber = cardNumber;
        this.cardCoName =cardCoName;
        this.idx= idx;

    }
}
