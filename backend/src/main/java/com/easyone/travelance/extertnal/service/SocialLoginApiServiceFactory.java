package com.easyone.travelance.extertnal.service;


import com.easyone.travelance.domain.member.constant.SocialType;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SocialLoginApiServiceFactory {

    private static Map<String, SocialLoginApiService> socialLoginApiServices;

    public SocialLoginApiServiceFactory(Map<String, SocialLoginApiService> socialLoginApiServices) {
        SocialLoginApiServiceFactory.socialLoginApiServices = socialLoginApiServices;
    }

    // 소셜 타입에 맞춰서 저장
    public static SocialLoginApiService getSocialLoginApiService(SocialType socialType) {
        String socialLoginApiServiceBeanName = "";

        if (SocialType.KAKAO.equals(socialType)) {
            socialLoginApiServiceBeanName = "kakaoLoginApiServiceImpl";
        }

        return socialLoginApiServices.get(socialLoginApiServiceBeanName);
    }
}
