package com.easyone.travelance.domain.account.dto;

import lombok.Data;

@Data
public class SelectedAccountRequestDto {
    private String account;
    private String bankName;
    private Long idx;

    // Jackson 역직렬화를 위한 기본 생성자 추가
    public SelectedAccountRequestDto() {}

    public SelectedAccountRequestDto(String account, String bankName, Long idx){
        this.account = account;
        this.bankName = bankName;
        this.idx = idx;
    }
}
