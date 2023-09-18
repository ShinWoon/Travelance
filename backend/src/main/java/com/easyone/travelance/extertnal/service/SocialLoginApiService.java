package com.easyone.travelance.extertnal.service;


import com.easyone.travelance.extertnal.model.OauthAttributes;

public interface SocialLoginApiService {

    OauthAttributes getUserInfo(String accessToken);
}
