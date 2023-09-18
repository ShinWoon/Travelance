package com.easyone.travelance.global.memberInfo;


import com.easyone.travelance.domain.member.constant.Role;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
// JWT에서 추출한 정보
public class MemberInfoDto {

    private String email;
    private Role role;
}
