package com.easyone.travelance.domain.member.dto;

import com.easyone.travelance.domain.member.entity.MainAccount;
import com.easyone.travelance.domain.member.entity.Member;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OneAccountDto {
    String account;

    public static OneAccountDto of(MainAccount mainAccount){
        return OneAccountDto.builder()
                .account(mainAccount.getOneAccount())
                .build();
    }
}
