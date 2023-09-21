package com.easyone.travelance.domain.account.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OneCheckRequestDto {
    private String name;
    private String bankName;
    private String account;
    private String verifyCode;

    public OneCheckRequestDto(String name, String bankName, String account, String verifyCode){
        this.name = name;
        this.bankName = bankName;
        this.account = account;
        this.verifyCode = verifyCode;
    }
}
