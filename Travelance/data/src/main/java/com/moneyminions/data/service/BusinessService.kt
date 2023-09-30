package com.moneyminions.data.service

import com.moneyminions.data.model.common.response.CommonResponse
import com.moneyminions.data.model.home.request.UseCashRequest
import com.moneyminions.data.model.home.response.TravelRoomInfoResponse
import com.moneyminions.data.model.login.request.AuthenticationAccountRequest
import com.moneyminions.data.model.login.request.LoginRequest
import com.moneyminions.data.model.login.request.MemberInfoRequest
import com.moneyminions.data.model.login.response.AccountResponse
import com.moneyminions.data.model.login.response.AuthenticationAccountResponse
import com.moneyminions.data.model.login.response.CardResponse
import com.moneyminions.data.model.login.response.JoinResponse
import com.moneyminions.data.model.login.response.LoginResponse
import com.moneyminions.data.model.login.response.MemberInfoResponse
import com.moneyminions.data.model.login.response.ReAccessTokenResponse
import com.moneyminions.data.model.travellist.request.RoomInfoRequestDto
import com.moneyminions.data.model.travellist.request.RoomUserRequestDto
import com.moneyminions.data.model.travellist.response.TravelRoomResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query


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
    @Multipart
    @POST("/travel/room")
    suspend fun createTravelRoom(
        @Part imageFiles: MultipartBody.Part?,
        @Part("roomUserRequestDto") roomUserRequestDto: RoomUserRequestDto,
        @Part("roomInfoRequestDto") roomInfoRequestDto: RoomInfoRequestDto,
    ): CommonResponse
    
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
    /**
     * 닉네임 수정
     */
    @PATCH("member/update/nickname")
    suspend fun updateNickname(@Query("nickname") nickname: String): CommonResponse
    /**
     * ACCESS 토큰 재발급
     */
    @POST("api/accounts/access-token/re")
    suspend fun postReAccessToken(@Header("Authorization") refreshToken: String): Response<ReAccessTokenResponse>

    /**
     * 여행방 삭제 API
     */
    @DELETE("travel/room/{roomId}")
    suspend fun deleteTravelRoom(@Path(value = "roomId") roomId: Int): CommonResponse
    
    /**
     * 여행방 생성 API
     */
    @POST("travel/room/{roomId}/start")
    suspend fun startTravel(@Path(value = "roomId") roomId: Int): CommonResponse
    
    /**
     * 특정 여행방 조회 API
     */
    @GET("travel/room/{roomId}")
    suspend fun getTravelRoomInfo(@Path(value = "roomId") roomId: Int): TravelRoomInfoResponse
    
    /**
     * 현급 입력 API
     */
    @POST("payment/cash")
    suspend fun requestUseCash(@Body useCashRequest: UseCashRequest): CommonResponse
}