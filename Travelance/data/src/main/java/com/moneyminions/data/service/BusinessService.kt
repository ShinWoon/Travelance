package com.moneyminions.data.service

import com.moneyminions.data.model.login.request.AuthenticationAccountRequest
import com.moneyminions.data.model.login.request.LoginRequest
import com.moneyminions.data.model.login.response.AuthenticationAccountResponse
import com.moneyminions.data.model.login.response.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface BusinessService {

    /**
     * 로그인 api
     */
    @POST("api/oauth/login")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse

    /**
     * 1원 이체 요청
     */
    @POST("account/1request")
    suspend fun postAuthenticationAccount(@Body authenticationAccountRequest: AuthenticationAccountRequest): AuthenticationAccountResponse
}