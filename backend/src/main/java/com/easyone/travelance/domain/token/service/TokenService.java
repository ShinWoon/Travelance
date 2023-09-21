package com.easyone.travelance.domain.token.service;


import com.easyone.travelance.domain.member.entity.Member;
import com.easyone.travelance.domain.member.service.MemberService;
import com.easyone.travelance.domain.token.dto.AccessTokenResponseDto;
import com.easyone.travelance.global.jwt.constant.GrantType;
import com.easyone.travelance.global.jwt.service.TokenManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
@RequiredArgsConstructor
public class TokenService {

    private final MemberService memberService;
    private final TokenManager tokenManager;

    // accessToken 재발급, refresh는 로그인때만 재발급
    public AccessTokenResponseDto createAccessTokenByRefreshToken(String refreshToken) {
        // refresh토큰으로 사용자 찾고
        Member member = memberService.findMemberByRefreshToken(refreshToken);

        // 만료기간, email, role 설정해서 access 토큰 재발급
        Date accessTokenExpirePeriod = tokenManager.createAccessTokenExpirationPeriod();
        String accessToken = tokenManager.createAccessToken(member.getEmail(), member.getRole(), accessTokenExpirePeriod);

        return AccessTokenResponseDto.builder()
                .grantType(GrantType.BEARER.getType())
                .accessToken(accessToken)
                .accessTokenExpirePeriod(accessTokenExpirePeriod)
                .role(member.getRole())
                .build();
    }
}
