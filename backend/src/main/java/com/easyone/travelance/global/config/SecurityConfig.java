package com.easyone.travelance.global.config;

import com.easyone.travelance.global.jwt.service.TokenManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

    @Value("${jwt.secretKey}")
    private String secretKey;

    @Value("${jwt.access.expiration}")
    private String accessTokenExpirationPeriod;

    @Value("${jwt.refresh.expiration}")
    private String refreshTokenExpirationPeriod;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors(Customizer.withDefaults())
                .formLogin().disable();
        return http.build();
    }
    @Bean
    public TokenManager tokenManager() {
        return new TokenManager(accessTokenExpirationPeriod, refreshTokenExpirationPeriod, secretKey);

    }
}
