package com.easyone.travelance.extertnal.kakao.controller;


import com.easyone.travelance.extertnal.kakao.client.KakaoTokenClient;
import com.easyone.travelance.extertnal.kakao.dto.KakaoTokenDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Tag(name = "Auth", description = "소셜로그인 관련 api")
@Controller
@Slf4j
@RequiredArgsConstructor
public class KakaoTokenController {
    private final KakaoTokenClient kakaoTokenClient;

    @Value("${kakao.client.id}")
    private String clientId;

    @Value("${kakao.client.secret}")
    private String clientSecret;

    // 로그인 폼
    @GetMapping("/login")
    public String login() {
        return "loginForm";
    }

    // 인가코드 콜백 결과 ( 토큰값 있음)
    @Operation(summary = "카카오 토큰", description = "카카오 로그인 토큰 메서드입니다.")
    @GetMapping("/oauth/kakao/callback")
    public @ResponseBody String loginCallback(String code) {
       log.info("====================카카오토큰컨트롤러 들어왔음!!!!!===================");
        log.info(code);
        String contentType = "application/x-www-form-urlencoded;charset=utf-8";
        KakaoTokenDto.Request kakaoTokenRequestDto = KakaoTokenDto.Request.builder()
                .client_id(clientId)
                .client_secret(clientSecret)
                .grant_type("authorization_code")
                .code(code)
               // .redirect_url("http://localhost:8081/oauth/kakao/callback")
//                .redirect_url("http://j9d210.p.ssafy.io:8081/oauth/kakao/callback")
                .redirect_url("https://j9d210.p.ssafy.io/oauth/kakao/callback")
                .build();

        log.info(String.valueOf(kakaoTokenRequestDto.getRedirect_url()));
        KakaoTokenDto.Response kakaoToken = kakaoTokenClient.requestKakaoToken(contentType, kakaoTokenRequestDto);
        log.info("=====================================kakao token : " + kakaoToken);
        return "kakao token : " + kakaoToken;
    }

}
