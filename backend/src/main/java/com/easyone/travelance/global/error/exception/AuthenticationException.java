package com.easyone.travelance.global.error.exception;


import com.easyone.travelance.global.error.ErrorCode;

public class AuthenticationException extends BusinessException {

    public AuthenticationException(ErrorCode errorCode) {
        super(errorCode);
    }

}
