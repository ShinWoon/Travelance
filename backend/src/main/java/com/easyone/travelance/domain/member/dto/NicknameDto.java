package com.easyone.travelance.domain.member.dto;

import com.easyone.travelance.domain.member.entity.Member;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NicknameDto {

    String nickname;

    public static NicknameDto of(Member member){
        return NicknameDto.builder()
                .nickname(member.getNickname())
                .build();
    }
}
