package com.easyone.travelance.extertnal.model;


import com.easyone.travelance.domain.member.constant.Role;
import com.easyone.travelance.domain.member.constant.SocialType;
import com.easyone.travelance.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter @Builder
public class OauthAttributes { // 회원 정보 가져올 때 통일시킴

    private String nickname;
    private String email;

    public Member toMemberEntity(SocialType socialType) {
        return Member.builder()
                .email(email)
                .nickname(nickname)
                .role(Role.GUEST)
                .build();
    }

}
