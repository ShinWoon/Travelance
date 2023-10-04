package com.easyone.travelance.api.logout.controller;


import com.easyone.travelance.api.logout.service.LogoutService;
import com.easyone.travelance.domain.common.ResultDto;
import com.easyone.travelance.global.util.AuthorizationHeaderUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Tag(name = "Auth", description = "소셜로그인 관련 api")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/oauth")
public class LogoutController {

    private final LogoutService logoutService;

    // 로그아웃
    @Operation(summary = "로그아웃", description = "로그아웃을 수행합니다."+"\n\n### [ 수행절차 ]\n\n"+"- try it out 해주세요\n\n")
    @PostMapping("/logout")
    public ResponseEntity<ResultDto> logout(HttpServletRequest httpServletRequest) {
        // 헤더 검증
        String authorizationHeader = httpServletRequest.getHeader("Authorization");
        AuthorizationHeaderUtils.validateAuthorization(authorizationHeader);

        String accessToken = authorizationHeader.split(" ")[1];
        logoutService.logout(accessToken);

        return new ResponseEntity<>(new ResultDto("로그아웃"), HttpStatus.OK);
    }
}
