package com.easyone.travelance.domain.account.dto;

import lombok.Data;

@Data
public class SelectedRequestDto {
    private String account;
    private String bankName;
    private String accountUrl;

    // Jackson 역직렬화를 위한 기본 생성자 추가
    public SelectedRequestDto() {}

    public SelectedRequestDto(String account, String bankName, String accountUrl){
        this.account = account;
        this.bankName = bankName;
        this.accountUrl = accountUrl;
    }
}
