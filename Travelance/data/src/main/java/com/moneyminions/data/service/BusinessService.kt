package com.moneyminions.data.service

import com.moneyminions.data.model.request.LoginRequest
import com.moneyminions.data.model.response.login.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface BusinessService {

    /**
     * 로그인 API
     */
    @POST("api/oauth/login")
    suspend fun login(@Body loginRequest: LoginRequest = LoginRequest("KAKAO")): LoginResponse
    
    
    /**
     * 여행방 만들기 API
     */
    @POST("travel/room")
    suspend fun createTravelRoom(@Body createTravelRoomRequest: CreateTravelRoomRequest)

}