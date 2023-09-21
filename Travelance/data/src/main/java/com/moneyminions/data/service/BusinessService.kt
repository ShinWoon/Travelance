package com.moneyminions.data.service

import com.moneyminions.data.model.request.LoginRequest
import com.moneyminions.data.model.response.login.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface BusinessService {

    /**
     * 로그인 api
     */
    @POST("api/oauth/login")
    suspend fun login(@Body loginRequest: LoginRequest = LoginRequest("KAKAO")): LoginResponse

}