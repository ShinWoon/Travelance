package com.moneyminions.data.service

import com.moneyminions.data.model.common.response.CommonResponse
import com.moneyminions.data.model.login.response.AccountResponse
import com.moneyminions.data.model.login.request.AuthenticationAccountRequest
import com.moneyminions.data.model.login.request.LoginRequest
import com.moneyminions.data.model.login.request.MemberInfoRequest
import com.moneyminions.data.model.login.response.AuthenticationAccountResponse
import com.moneyminions.data.model.login.response.CardResponse
import com.moneyminions.data.model.login.response.JoinResponse
import com.moneyminions.data.model.login.response.LoginResponse
import com.moneyminions.data.model.login.response.MemberInfoResponse
import retrofit2.http.Body
import retrofit2.http.PATCH
import com.moneyminions.data.model.travellist.request.CreateTravelRoomRequest
import com.moneyminions.data.model.travellist.response.TravelRoomResponse
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
    suspend fun getAccountList(): List<AccountResponse>
    /**
     * 내 전체 카드 조회
     */
    @POST("card/allcard")
    suspend fun getCardList(): List<CardResponse>
    /**
     * 회원 등록
     */
    @PATCH("member/additional")
    suspend fun join(@Body memberInfoRequest: MemberInfoRequest): JoinResponse

    
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
    /**
     * 마이페이지 조회
     */
    @POST("member/mypage")
    suspend fun getMemberInfo(): MemberInfoResponse

}