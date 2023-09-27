package com.easyone.travelance.global.config;

import com.easyone.travelance.global.jwt.service.TokenManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Value("${jwt.secretKey}")
    private String secretKey;

    @Value("${jwt.access.expiration}")
    private String accessTokenExpirationPeriod;

    @Value("${jwt.refresh.expiration}")
    private String refreshTokenExpirationPeriod;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // CSRF 보호 비활성화
                .cors(Customizer.withDefaults()) // CORS 설정
                .formLogin().disable() // 폼 기반 로그인 비활성화
                .authorizeRequests()
                .anyRequest().permitAll(); // 모든 요청에 대해 허용

    }

    @Bean
    public TokenManager tokenManager() {
        return new TokenManager(accessTokenExpirationPeriod, refreshTokenExpirationPeriod, secretKey);

    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:8081", "https://kauth.kakao.com","http://ec2-3-34-155-173.ap-northeast-2.compute.amazonaws.com:8080", "http://j9d210.p.ssafy.io:8081","http://3.39.110.134:8083/","https://j9d210.p.ssafy.io")); // 허용할 원본 도메인
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE")); // 허용할 HTTP 메서드
        configuration.setAllowCredentials(false); // 쿠키 등 자격 증명과 함께 요청을 할 수 있도록 허용
        configuration.setAllowedHeaders(Arrays.asList("accept", "Authorization", "Cache-Control", "Content-Type")); // 허용할 헤더

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // 모든 경로에 대해 위에서 정의한 CORS 설정 적용
        return source;
    }

    @Bean
    public BCryptPasswordEncoder encoderPassword() {
        return new BCryptPasswordEncoder();
    }
}

//public class SecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
//
//    @Value("${jwt.secretKey}")
//    private String secretKey;
//
//    @Value("${jwt.access.expiration}")
//    private String accessTokenExpirationPeriod;
//
//    @Value("${jwt.refresh.expiration}")
//    private String refreshTokenExpirationPeriod;
//
//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//        http
//                .csrf().disable() // CSRF 보호 비활성화
//                .cors(Customizer.withDefaults()) // CORS 설정
//                .formLogin().disable() // 폼 기반 로그인 비활성화
//                .authorizeRequests()
//                .antMatchers("/spiiters/me").hasRole("SPITTER")
//                .antMatchers(HttpMethod.POST, "/spittles").hasRole("SPITTER")
//                .anyRequest().permitAll()
//                .and()
//                .requiresChannel()
//                .antMatchers("/spitter/form").requiresSecure();
//    }
