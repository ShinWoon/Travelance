package com.moneyminions.data.service

import com.moneyminions.data.datasource.remote.common.response.CommonResponse
import com.moneyminions.data.model.login.request.AuthenticationAccountRequest
import com.moneyminions.data.model.login.request.LoginRequest
import com.moneyminions.data.model.login.response.AuthenticationAccountResponse
import com.moneyminions.data.model.login.response.LoginResponse
import com.moneyminions.data.model.travellist.request.CreateTravelRoomRequest
import com.moneyminions.data.model.travellist.response.TravelRoomResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface BusinessService {

    /**
     * 로그인 API
     */
    @POST("api/oauth/login")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse

    /**
     * 1원 이체 요청
     */
    @POST("account/1request")
    suspend fun postAuthenticationAccount(@Body authenticationAccountRequest: AuthenticationAccountRequest): AuthenticationAccountResponse

    /**
     * 1원 이체 확인
     */
    @POST("account/1response")
    suspend fun confirmAuthenticationAccount(@Body authenticationAccountRequest: AuthenticationAccountRequest): CommonResponse

    /**
     * 내 전체 계좌 조회
     */
    @POST("account/allaccount")
    suspend fun getAccountList()
    
    /**
     * 여행방 만들기 API
     */
    @POST("travel/room")
    suspend fun createTravelRoom(@Body createTravelRoomRequest: CreateTravelRoomRequest): String

    /**
     * 여행 목록 요청 API
     */
    @GET("travel/room")
    suspend fun travelList(): MutableList<TravelRoomResponse>

}