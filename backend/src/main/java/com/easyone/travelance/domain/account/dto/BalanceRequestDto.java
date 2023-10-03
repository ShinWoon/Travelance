package com.easyone.travelance.domain.account.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BalanceRequestDto {
    String privateId;
    String account;

    public BalanceRequestDto(String privateId, String account) {
        this.privateId = privateId;
        this.account = account;
    }
}
