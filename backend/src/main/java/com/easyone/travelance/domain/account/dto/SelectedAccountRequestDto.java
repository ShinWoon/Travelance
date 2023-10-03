package com.easyone.travelance.domain.account.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SelectedAccountRequestDto {
    private String account;
    private String bankName;
    private Long idx;


    public SelectedAccountRequestDto(String account, String bankName, Long idx){
        this.account = account;
        this.bankName = bankName;
        this.idx = idx;
    }
}
