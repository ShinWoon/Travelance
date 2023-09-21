package com.moneyminions.data.service

import com.moneyminions.data.model.login.request.LoginRequest
import com.moneyminions.data.model.login.response.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface BusinessService {

    /**
     * 로그인 api
     */
    @POST("api/oauth/login")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse

}