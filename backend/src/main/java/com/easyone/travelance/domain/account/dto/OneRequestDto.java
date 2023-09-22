package com.easyone.travelance.domain.account.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OneRequestDto {
    private String name;
    private String bankName;
    private String account;
    private String verifyCode;

    public OneRequestDto(String name, String bankName, String account){
        this.name = name;
        this.bankName = bankName;
        this.account = account;
    }
}
