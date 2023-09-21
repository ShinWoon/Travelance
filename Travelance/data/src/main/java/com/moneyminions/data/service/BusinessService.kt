package com.moneyminions.data.service

import com.moneyminions.data.model.login.request.LoginRequest
import com.moneyminions.data.model.login.response.LoginResponse
import com.moneyminions.data.model.travellist.request.CreateTravelRoomRequest
import com.moneyminions.data.model.travellist.request.TravelListRequest
import com.moneyminions.data.model.travellist.response.TravelRoomResponse
import retrofit2.http.Body
import retrofit2.http.GET
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

    /**
     * 여행 목록 요청 API
     */
    @GET("travel/room")
    suspend fun travelList(): List<TravelRoomResponse>
}