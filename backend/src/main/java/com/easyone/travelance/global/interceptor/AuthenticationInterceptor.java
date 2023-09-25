package com.easyone.travelance.global.interceptor;


import com.easyone.travelance.global.error.ErrorCode;
import com.easyone.travelance.global.error.exception.AuthenticationException;
import com.easyone.travelance.global.jwt.constant.TokenType;
import com.easyone.travelance.global.jwt.service.TokenManager;
import com.easyone.travelance.global.util.AuthorizationHeaderUtils;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationInterceptor implements HandlerInterceptor {

    private final TokenManager tokenManager;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        System.out.println("=============================Auth=========================");
        log.info("Auth 인터셉터");
        // 1. Authorization Header 검증
        String authorizationHeader = request.getHeader("Authorization");
        AuthorizationHeaderUtils.validateAuthorization(authorizationHeader);

        // 2. 토큰 검증
        String token = authorizationHeader.split(" ")[1];
        tokenManager.validateToken(token);

        // 3. 토큰 타입 (AccessToken 일때만 통과)
        Claims tokenClaims = tokenManager.getTokenClaims(token);
        String tokenType = tokenClaims.getSubject();
        if (!TokenType.isAccessToken(tokenType)) {
            throw new AuthenticationException(ErrorCode.NOT_ACCESS_TOKEN_TYPE);
        }

        return true;
    }
}
