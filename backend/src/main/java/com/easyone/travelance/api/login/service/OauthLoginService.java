package com.easyone.travelance.api.login.service;


import com.easyone.travelance.api.login.dto.OauthLoginDto;
import com.easyone.travelance.domain.member.constant.SocialType;
import com.easyone.travelance.domain.member.entity.Member;
//import com.easyone.travelance.domain.member.entity.MemberAuth;
import com.easyone.travelance.domain.member.service.MemberService;
import com.easyone.travelance.extertnal.model.OauthAttributes;
import com.easyone.travelance.extertnal.service.SocialLoginApiService;
import com.easyone.travelance.extertnal.service.SocialLoginApiServiceFactory;
import com.easyone.travelance.global.jwt.dto.JwtDto;
import com.easyone.travelance.global.jwt.service.TokenManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OauthLoginService {

    private final MemberService memberService;
    private final TokenManager tokenManager;

    public OauthLoginDto.Response oauthLogin(String accessToken, SocialType socialType, String firebaseToken) {
        SocialLoginApiService socialLoginApiService = SocialLoginApiServiceFactory.getSocialLoginApiService(socialType);
        OauthAttributes userInfo = socialLoginApiService.getUserInfo(accessToken);
        log.info("userInfo : {}", userInfo);

        JwtDto jwtDto;
        Optional<Member> optionalMember = memberService.findByEmail(userInfo.getEmail());
        Member oauthMember;
        if (optionalMember.isEmpty()) { // 신규 회원가입
            oauthMember = userInfo.toMemberEntity(socialType);
            if (firebaseToken.isEmpty() || firebaseToken.isBlank()){
                throw new RuntimeException("fcm토큰이 없습니다");
            }
            else{
                // Firebase 토큰 값 설정
                oauthMember.setFirebaseToken(firebaseToken);
                oauthMember = memberService.registerMember(oauthMember);
            }
        } else { // 기존 회원
            oauthMember = optionalMember.get();
            // 기존 회원의 Firebase 토큰 업데이트
            if (shouldUpdateFirebaseToken(oauthMember, firebaseToken)) {
                oauthMember.setFirebaseToken(firebaseToken);
                memberService.updateMember(oauthMember); // 회원 정보 업데이트
            }
        }

        // 토큰 생성
        jwtDto = tokenManager.createJwtDto(oauthMember.getEmail(), oauthMember.getRole());
        oauthMember.updateRefreshToken(jwtDto);

        return OauthLoginDto.Response.of(jwtDto, oauthMember.getRole());
    }

    private boolean shouldUpdateFirebaseToken(Member member, String firebaseToken) {
        // 항상 업데이트하도록 true를 반환합니다.
        return true;
    }

}
