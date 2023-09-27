package com.easyone.travelance.global.config;


import com.easyone.travelance.global.interceptor.AdminAuthorizationInterceptor;
import com.easyone.travelance.global.interceptor.AuthenticationInterceptor;
import com.easyone.travelance.global.interceptor.MemberAuthorizationInterceptor;
import com.easyone.travelance.global.memberInfo.MemberInfoArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final AuthenticationInterceptor authenticationInterceptor;
    private final AdminAuthorizationInterceptor adminAuthorizationInterceptor;
    private final MemberInfoArgumentResolver memberInfoArgumentResolver;
    private final MemberAuthorizationInterceptor memberAuthorizationInterceptor;


    // HTTP 요청을 처리하는 과정에 인터셉터를 추가
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor)
                .order(1) // 가장 먼저 인증 인터셉터가 실행
                .addPathPatterns("/api/**")
                .excludePathPatterns("/api/oauth/login",
                        "/api/accounts/access-token/re",
                        "/api/oauth/logout",
                        "/api/feign/**");

        registry.addInterceptor(memberAuthorizationInterceptor)
                .order(2)
                .addPathPatterns("/api/**")
                .excludePathPatterns("/api/member/additional",
                        "/api/accounts/access-token/re",
                        "/api/oauth/logout",
                        "/api/oauth/login");

//        registry.addInterceptor(adminAuthorizationInterceptor)
//                .order(2)
//                .addPathPatterns("/api/admin/**");
    }

    // 컨트롤러 메서드의 매개변수를 해석 ( JWT 해석해서 정보 추출)
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(memberInfoArgumentResolver);
    }
}