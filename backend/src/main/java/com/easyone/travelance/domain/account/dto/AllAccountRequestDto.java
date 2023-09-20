package com.easyone.travelance.domain.account.dto;

import lombok.Data;

@Data
public class AllAccountRequestDto {
    String privateId;

    public AllAccountRequestDto(String privateId){
        this.privateId = privateId;
    }
}
