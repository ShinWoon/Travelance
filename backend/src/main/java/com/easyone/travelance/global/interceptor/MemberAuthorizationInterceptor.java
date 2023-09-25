package com.easyone.travelance.global.interceptor;


import com.easyone.travelance.domain.member.constant.Role;
import com.easyone.travelance.global.error.ErrorCode;
import com.easyone.travelance.global.error.exception.AuthenticationException;
import com.easyone.travelance.global.jwt.service.TokenManager;
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
public class MemberAuthorizationInterceptor implements HandlerInterceptor {

    private final TokenManager tokenManager;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        System.out.println("=============================member Auth=========================");
        log.info("MemberAuthorizationInter셉터임");
        String authorizationHeader = request.getHeader("Authorization");
        String accessToken = authorizationHeader.split(" ")[1];

        // Role이 MEMBER인지만 확인
        Claims tokenClaims = tokenManager.getTokenClaims(accessToken);
        String role = (String) tokenClaims.get("role");
        if (Role.GUEST.equals(Role.valueOf(role))) {
            throw new AuthenticationException(ErrorCode.FORBIDDEN_MEMBER);
        }

        return true;
    }
}
