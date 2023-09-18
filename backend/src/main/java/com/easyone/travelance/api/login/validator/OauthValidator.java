package com.easyone.travelance.api.login.validator;


import com.easyone.travelance.domain.member.constant.SocialType;
import com.easyone.travelance.global.error.ErrorCode;
import com.easyone.travelance.global.error.exception.BusinessException;
import org.springframework.stereotype.Service;

@Service
public class OauthValidator {

    public void validateSocialType(String socialType) {
        if (!SocialType.isSocialType(socialType)) {
            throw new BusinessException(ErrorCode.INVALID_SOCIAL_TYPE);
        }
    }
}
