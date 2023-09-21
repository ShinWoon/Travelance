package com.easyone.travelance.domain.member.dto.response;


import com.easyone.travelance.domain.member.constant.Role;
import com.easyone.travelance.domain.member.constant.SocialType;
import com.easyone.travelance.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberInfoResponseDto {
    // 회원 정보 조회 Dto

    private Long memberId;

    private String email;

    private String nickname;


    private SocialType socialType;

    private Role role;

    public static MemberInfoResponseDto of(Member member) {
        return MemberInfoResponseDto.builder()
                .memberId(member.getId())
                .nickname(member.getNickname())
                .email(member.getEmail())
                .role(member.getRole())
                .build();
    }
}
